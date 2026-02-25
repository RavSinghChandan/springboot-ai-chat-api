package com.chandan.ai.chatapi.client;

import com.chandan.ai.chatapi.exception.AiServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class AiClientImpl implements AiClient {

    private static final Logger log =
            LoggerFactory.getLogger(AiClientImpl.class);

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final MeterRegistry meterRegistry;

    private final Timer latencyTimer;
    private final Counter failureCounter;

    @Value("${ai.api.model}")
    private String model;

    @Value("${ai.api.url}")
    private String apiUrl;

    @Value("${ai.api.key}")
    private String apiKey;

    public AiClientImpl(WebClient webClient,
                        ObjectMapper objectMapper,
                        MeterRegistry meterRegistry) {

        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.meterRegistry = meterRegistry;

        this.latencyTimer = Timer.builder("ai.api.latency")
                .description("AI API response time")
                .register(meterRegistry);

        this.failureCounter = Counter.builder("ai.api.failures")
                .description("AI API failure count")
                .register(meterRegistry);
    }

    @Override
    @Retry(name = "aiRetry")
    @CircuitBreaker(name = "aiCircuit", fallbackMethod = "fallbackResponse")
    public Mono<String> getResponse(String prompt) {

        Timer.Sample sample = Timer.start(meterRegistry);

        return webClient.post()
                .uri(apiUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "model", model,
                        "input", prompt
                ))
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractText)
                .doOnSuccess(response ->
                        sample.stop(latencyTimer)
                )
                .doOnError(error -> {
                    failureCounter.increment();
                    sample.stop(latencyTimer);
                    log.error("Error while calling AI API", error);
                });
    }

    private String extractText(String json) {

        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode outputArray = root.path("output");

            for (JsonNode node : outputArray) {
                if ("message".equals(node.path("type").asText())) {
                    return node
                            .path("content")
                            .get(0)
                            .path("text")
                            .asText();
                }
            }

            throw new AiServiceException("AI response format unexpected.");

        } catch (Exception e) {
            failureCounter.increment();
            log.error("Failed to parse AI response", e);
            throw new AiServiceException("Failed to parse AI response.");
        }
    }

    public Mono<String> fallbackResponse(String prompt, Throwable throwable) {
        failureCounter.increment();
        log.error("Circuit breaker activated.", throwable);
        return Mono.just("AI service is temporarily unavailable. Please try again later.");
    }
}
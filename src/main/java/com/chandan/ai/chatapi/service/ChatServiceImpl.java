package com.chandan.ai.chatapi.service;

import com.chandan.ai.chatapi.client.AiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ChatServiceImpl implements ChatService {

    private static final Logger log =
            LoggerFactory.getLogger(ChatServiceImpl.class);

    private final AiClient aiClient;

    public ChatServiceImpl(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    // Project 1 – Normal Response
    @Override
    public Mono<String> chat(String message) {

        log.info("Received chat request. Message length={}", message.length());

        long startTime = System.currentTimeMillis();

        return aiClient.getResponse(message)
                .doOnSuccess(response -> {
                    long duration = System.currentTimeMillis() - startTime;
                    log.info("AI response generated in {} ms", duration);
                })
                .doOnError(error -> {
                    long duration = System.currentTimeMillis() - startTime;
                    log.error("AI request failed after {} ms", duration, error);
                });
    }

    // Project 2 – Streaming Response
    @Override
    public Flux<String> streamChat(String message) {

        log.info("Received streaming chat request. Message length={}", message.length());

        long startTime = System.currentTimeMillis();

        return aiClient.streamResponse(message)
                .doOnComplete(() -> {
                    long duration = System.currentTimeMillis() - startTime;
                    log.info("Streaming completed in {} ms", duration);
                })
                .doOnError(error -> {
                    long duration = System.currentTimeMillis() - startTime;
                    log.error("Streaming failed after {} ms", duration);
                });
    }
}
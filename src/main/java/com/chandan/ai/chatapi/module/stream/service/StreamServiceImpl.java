package com.chandan.ai.chatapi.module.stream.service;

import com.chandan.ai.chatapi.client.AiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class StreamServiceImpl implements StreamService {

    private static final Logger log =
            LoggerFactory.getLogger(StreamServiceImpl.class);

    private final AiClient aiClient;

    public StreamServiceImpl(AiClient aiClient) {
        this.aiClient = aiClient;
    }

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

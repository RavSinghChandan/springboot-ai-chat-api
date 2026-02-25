package com.chandan.ai.chatapi.client;

import reactor.core.publisher.Mono;

public interface AiClient {
    public Mono<String> getResponse(String prompt);
}

package com.chandan.ai.chatapi.service;

import reactor.core.publisher.Mono;

public interface ChatService {
    public Mono<String> chat(String message);
}

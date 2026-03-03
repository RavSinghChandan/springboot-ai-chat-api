package com.chandan.ai.chatapi.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatService {

    Mono<String> chat(String message);

    Flux<String> streamChat(String message);
}
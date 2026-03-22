package com.chandan.ai.chatapi.module.chat.service;

import reactor.core.publisher.Mono;

public interface ChatService {

    Mono<String> chat(String message);
}

package com.chandan.ai.chatapi.module.stream.service;

import reactor.core.publisher.Flux;

public interface StreamService {

    Flux<String> streamChat(String message);
}

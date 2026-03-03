package com.chandan.ai.chatapi.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AiClient {

    Mono<String> getResponse(String prompt);

    Flux<String> streamResponse(String prompt);
}
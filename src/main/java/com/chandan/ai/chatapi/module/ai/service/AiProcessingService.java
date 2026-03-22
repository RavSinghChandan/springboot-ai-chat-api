package com.chandan.ai.chatapi.module.ai.service;

import com.chandan.ai.chatapi.module.ai.dto.*;
import reactor.core.publisher.Mono;

public interface AiProcessingService {

    Mono<SummarizeResponse> summarize(String text, Integer maxLength);

    Mono<ClassifyResponse> classify(String text, java.util.List<String> categories);

    Mono<KeywordsResponse> extractKeywords(String text, int maxKeywords);
}

package com.chandan.ai.chatapi.module.ai.service;

import com.chandan.ai.chatapi.core.AIOrchestratorService;
import com.chandan.ai.chatapi.module.ai.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiProcessingServiceImpl implements AiProcessingService {

    private static final Logger log = LoggerFactory.getLogger(AiProcessingServiceImpl.class);

    private final AIOrchestratorService aiOrchestrator;

    public AiProcessingServiceImpl(AIOrchestratorService aiOrchestrator) {
        this.aiOrchestrator = aiOrchestrator;
    }

    @Override
    public Mono<SummarizeResponse> summarize(String text, Integer maxLength) {
        log.info("AI summarize request, text length={}, maxLength={}", text.length(), maxLength);
        return aiOrchestrator.summarize(text, maxLength)
                .map(SummarizeResponse::new);
    }

    @Override
    public Mono<ClassifyResponse> classify(String text, java.util.List<String> categories) {
        log.info("AI classify request, text length={}, categories={}", text.length(), categories);
        return aiOrchestrator.classify(text, categories)
                .map(response -> new ClassifyResponse(response.trim()));
    }

    @Override
    public Mono<KeywordsResponse> extractKeywords(String text, int maxKeywords) {
        log.info("AI keywords request, text length={}, maxKeywords={}", text.length(), maxKeywords);
        return aiOrchestrator.extractKeywords(text, maxKeywords)
                .map(this::parseKeywords);
    }

    private KeywordsResponse parseKeywords(String response) {
        if (response == null || response.isBlank()) {
            return new KeywordsResponse(List.of());
        }
        List<String> keywords = Arrays.stream(response.split("[,;]"))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .limit(20)
                .collect(Collectors.toList());
        return new KeywordsResponse(keywords);
    }
}

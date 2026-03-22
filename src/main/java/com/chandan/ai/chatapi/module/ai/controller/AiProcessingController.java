package com.chandan.ai.chatapi.module.ai.controller;

import com.chandan.ai.chatapi.module.ai.dto.*;
import com.chandan.ai.chatapi.module.ai.service.AiProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "AI Processing", description = "AI utilities - summarization, classification, keyword extraction")
@RestController
@RequestMapping("/api/ai")
public class AiProcessingController {

    private final AiProcessingService aiProcessingService;

    public AiProcessingController(AiProcessingService aiProcessingService) {
        this.aiProcessingService = aiProcessingService;
    }

    @Operation(summary = "Summarize", description = "Summarize text using AI", operationId = "summarize")
    @PostMapping("/summarize")
    public Mono<SummarizeResponse> summarize(@Valid @RequestBody SummarizeRequest request) {
        return aiProcessingService.summarize(request.getText(), request.getMaxLength());
    }

    @Operation(summary = "Classify", description = "Classify text into a category", operationId = "classify")
    @PostMapping("/classify")
    public Mono<ClassifyResponse> classify(@Valid @RequestBody ClassifyRequest request) {
        return aiProcessingService.classify(request.getText(), request.getCategories());
    }

    @Operation(summary = "Keywords", description = "Extract keywords from text using AI", operationId = "keywords")
    @PostMapping("/keywords")
    public Mono<KeywordsResponse> keywords(@Valid @RequestBody KeywordsRequest request) {
        int max = request.getMaxKeywords() != null && request.getMaxKeywords() > 0
                ? request.getMaxKeywords()
                : 10;
        return aiProcessingService.extractKeywords(request.getText(), max);
    }
}

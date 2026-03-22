package com.chandan.ai.chatapi.module.log.controller;

import com.chandan.ai.chatapi.module.log.service.LogAnalyzerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Tag(name = "Log Analyzer", description = "Log Analyzer - analyze production logs with AI")
@RestController
@RequestMapping("/api/logs")
public class LogAnalyzerController {

    private final LogAnalyzerService logAnalyzerService;

    public LogAnalyzerController(LogAnalyzerService logAnalyzerService) {
        this.logAnalyzerService = logAnalyzerService;
    }

    @Operation(summary = "Analyze Log", description = "Upload a .log or .txt file for AI-powered analysis (summary, root causes, recommendations)", operationId = "analyzeLog")
    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<String>> analyzeLog(
            @RequestPart("file") MultipartFile file) {

        return logAnalyzerService.analyze(file)
                .map(ResponseEntity::ok);
    }
}

package com.chandan.ai.chatapi.controller;

import com.chandan.ai.chatapi.service.LogAnalyzerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/logs")
public class LogAnalyzerController {

    private final LogAnalyzerService logAnalyzerService;

    public LogAnalyzerController(LogAnalyzerService logAnalyzerService) {
        this.logAnalyzerService = logAnalyzerService;
    }

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<String>> analyzeLog(
            @RequestPart("file") MultipartFile file) {

        return logAnalyzerService.analyze(file)
                .map(ResponseEntity::ok);
    }
}
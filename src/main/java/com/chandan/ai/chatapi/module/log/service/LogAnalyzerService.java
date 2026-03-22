package com.chandan.ai.chatapi.module.log.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface LogAnalyzerService {

    Mono<String> analyze(MultipartFile file);
}

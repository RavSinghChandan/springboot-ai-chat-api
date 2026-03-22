package com.chandan.ai.chatapi.module.rag.controller;

import com.chandan.ai.chatapi.module.rag.dto.RagQueryRequest;
import com.chandan.ai.chatapi.module.rag.dto.RagQueryResponse;
import com.chandan.ai.chatapi.module.rag.dto.RagUploadRequest;
import com.chandan.ai.chatapi.module.rag.dto.RagUploadResponse;
import com.chandan.ai.chatapi.module.rag.service.RagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Tag(name = "RAG", description = "RAG Engine - upload documents, query with retrieved context")
@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @Operation(summary = "Upload Text", description = "Upload document content as text for RAG indexing", operationId = "upload")
    @PostMapping("/upload")
    public Mono<RagUploadResponse> upload(@Valid @RequestBody RagUploadRequest request) {
        return ragService.uploadFromText(request.getContent(), request.getDocumentId());
    }

    @Operation(summary = "Upload File", description = "Upload a document file for RAG indexing", operationId = "uploadFile")
    @PostMapping(value = "/upload/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<RagUploadResponse> uploadFile(@RequestPart("file") MultipartFile file) {
        return ragService.uploadFromFile(file);
    }

    @Operation(summary = "Query", description = "Query the RAG index with a question; returns AI answer using retrieved context", operationId = "query")
    @PostMapping("/query")
    public Mono<RagQueryResponse> query(@Valid @RequestBody RagQueryRequest request) {
        return ragService.query(request.getQuestion(), request.getTopK());
    }
}

package com.chandan.ai.chatapi.module.rag.service;

import com.chandan.ai.chatapi.core.AIOrchestratorService;
import com.chandan.ai.chatapi.module.rag.dto.RagQueryResponse;
import com.chandan.ai.chatapi.module.rag.dto.RagUploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RagService {

    private static final Logger log = LoggerFactory.getLogger(RagService.class);
    private static final int CHUNK_SIZE = 500;
    private static final int CHUNK_OVERLAP = 50;

    private final VectorStore vectorStore;
    private final EmbeddingService embeddingService;
    private final AIOrchestratorService aiOrchestrator;

    public RagService(VectorStore vectorStore, EmbeddingService embeddingService, AIOrchestratorService aiOrchestrator) {
        this.vectorStore = vectorStore;
        this.embeddingService = embeddingService;
        this.aiOrchestrator = aiOrchestrator;
    }

    public Mono<RagUploadResponse> uploadFromText(String content, String documentId) {
        String id = documentId != null && !documentId.isBlank() ? documentId : UUID.randomUUID().toString();
        List<String> chunks = chunkText(content);
        vectorStore.store(id, chunks, embeddingService);
        log.info("RAG upload: documentId={}, chunks={}", id, chunks.size());
        return Mono.just(new RagUploadResponse(id, chunks.size()));
    }

    public Mono<RagUploadResponse> uploadFromFile(MultipartFile file) {
        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            String docId = file.getOriginalFilename() != null ? file.getOriginalFilename() : UUID.randomUUID().toString();
            return uploadFromText(content, docId);
        } catch (Exception e) {
            log.error("RAG upload file failed", e);
            return Mono.error(new RuntimeException("Failed to read file: " + e.getMessage()));
        }
    }

    public Mono<RagQueryResponse> query(String question, int topK) {
        double[] queryEmbedding = embeddingService.embed(question);
        var similar = vectorStore.searchSimilar(queryEmbedding, topK, embeddingService);

        if (similar.isEmpty()) {
            log.info("RAG query: no similar chunks found for question");
            return aiOrchestrator.complete(question)
                    .map(answer -> new RagQueryResponse(answer, 0));
        }

        String context = similar.stream()
                .map(VectorStore.StoredChunk::text)
                .collect(Collectors.joining("\n\n---\n\n"));

        return aiOrchestrator.completeWithContext(question, context)
                .map(answer -> new RagQueryResponse(answer, similar.size()));
    }

    private List<String> chunkText(String text) {
        if (text == null || text.isBlank()) return List.of();
        List<String> chunks = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + CHUNK_SIZE, text.length());
            if (end < text.length()) {
                int lastSpace = text.lastIndexOf(' ', end);
                if (lastSpace > start) end = lastSpace + 1;
            }
            String chunk = text.substring(start, end).trim();
            if (!chunk.isEmpty()) chunks.add(chunk);
            start = end - CHUNK_OVERLAP;
            if (start <= 0) start = end;
        }
        return chunks.isEmpty() ? List.of(text.trim()) : chunks;
    }
}

package com.chandan.ai.chatapi.module.rag.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory vector store for RAG. Stores document chunks with their embeddings.
 */
@Component
public class VectorStore {

    private final ConcurrentHashMap<String, List<StoredChunk>> docChunks = new ConcurrentHashMap<>();

    public record StoredChunk(String documentId, String text, double[] embedding) {}

    public void store(String documentId, List<String> chunks, EmbeddingService embeddingService) {
        List<StoredChunk> stored = chunks.stream()
                .map(c -> new StoredChunk(documentId, c, embeddingService.embed(c)))
                .toList();
        docChunks.put(documentId, new ArrayList<>(stored));
    }

    public List<StoredChunk> searchSimilar(double[] queryEmbedding, int topK, EmbeddingService embeddingService) {
        List<StoredChunk> all = docChunks.values().stream().flatMap(List::stream).toList();
        return all.stream()
                .map(c -> new ScoredChunk(c, embeddingService.cosineSimilarity(queryEmbedding, c.embedding)))
                .filter(s -> s.score > 0)
                .sorted(Comparator.<ScoredChunk>comparingDouble(s -> s.score).reversed())
                .limit(topK)
                .map(s -> s.chunk)
                .toList();
    }

    public int totalChunks() {
        return docChunks.values().stream().mapToInt(List::size).sum();
    }

    private record ScoredChunk(StoredChunk chunk, double score) {}
}

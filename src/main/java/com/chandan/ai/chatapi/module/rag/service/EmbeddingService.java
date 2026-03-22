package com.chandan.ai.chatapi.module.rag.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple embedding service using bag-of-words + TF for semantic-ish similarity.
 * No external API; suitable for MVP. Can be replaced with real embeddings (OpenAI, etc.) later.
 */
@Service
public class EmbeddingService {

    private static final int VOCAB_SIZE = 1000;
    private static final String SPLIT_REGEX = "\\s+|(?<=\\.)|(?=\\.)|(?<=,)|(?=,)|(?<=\\?)|(?=\\?)|(?<=!)|(?=!)";

    public double[] embed(String text) {
        if (text == null || text.isBlank()) {
            return new double[VOCAB_SIZE];
        }
        Map<Integer, Double> vec = new HashMap<>();
        String[] tokens = tokenize(text);
        for (String token : tokens) {
            int idx = Math.abs(token.hashCode() % VOCAB_SIZE);
            vec.merge(idx, 1.0, Double::sum);
        }
        return toNormalizedArray(vec);
    }

    public double cosineSimilarity(double[] a, double[] b) {
        double dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        if (normA == 0 || normB == 0) return 0;
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private String[] tokenize(String text) {
        return Arrays.stream(text.toLowerCase()
                        .replaceAll("[^a-zA-Z0-9\\s.,?!]", " ")
                        .split(SPLIT_REGEX))
                .map(String::trim)
                .filter(s -> s.length() > 1)
                .toArray(String[]::new);
    }

    private double[] toNormalizedArray(Map<Integer, Double> vec) {
        double[] arr = new double[VOCAB_SIZE];
        vec.forEach((k, v) -> arr[k] = v);
        double norm = Math.sqrt(Arrays.stream(arr).map(x -> x * x).sum());
        if (norm > 0) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] /= norm;
            }
        }
        return arr;
    }
}

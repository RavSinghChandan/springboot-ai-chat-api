package com.chandan.ai.chatapi.service;

import com.chandan.ai.chatapi.client.AiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class LogAnalyzerServiceImpl implements LogAnalyzerService {

    private final AiClient aiClient;

    // Simple in-memory history
    private final Map<String, Map<String, Object>> analysisHistory = new ConcurrentHashMap<>();

    public LogAnalyzerServiceImpl(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @Override
    public Mono<String> analyze(MultipartFile file) {

        try {

            validateFile(file);

            String rawLog = new String(file.getBytes(), StandardCharsets.UTF_8);

            // Extract only ERROR/WARN lines
            String filteredLog = filterImportantLines(rawLog);

            // Generate log statistics
            Map<String, Object> stats = generateStats(filteredLog);

            // Hybrid severity (rule-based first)
            String ruleSeverity = ruleBasedSeverity(filteredLog);

            String prompt = buildPrompt(filteredLog, stats, ruleSeverity);

            return aiClient.getResponse(prompt)
                    .map(response -> {

                        String structuredJson = buildStructuredJson(response, ruleSeverity);

                        // Save to history
                        analysisHistory.put(UUID.randomUUID().toString(), Map.of(
                                "timestamp", LocalDateTime.now(),
                                "fileName", file.getOriginalFilename(),
                                "severity", ruleSeverity,
                                "summary", structuredJson
                        ));

                        return structuredJson;
                    });

        } catch (IOException e) {
            return Mono.error(new RuntimeException("Failed to process file", e));
        }
    }

    // ---------------- FILE VALIDATION ----------------

    private void validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String name = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();

        if (!(name.endsWith(".log") || name.endsWith(".txt"))) {
            throw new RuntimeException("Only .log and .txt files are allowed");
        }
    }

    // ---------------- LOG FILTERING ----------------

    private String filterImportantLines(String rawLog) {

        return Arrays.stream(rawLog.split("\n"))
                .filter(line -> line.contains("ERROR") || line.contains("WARN"))
                .collect(Collectors.joining("\n"));
    }

    // ---------------- LOG STATISTICS ----------------

    private Map<String, Object> generateStats(String log) {

        List<String> lines = Arrays.asList(log.split("\n"));

        long errorCount = lines.stream()
                .filter(line -> line.contains("ERROR"))
                .count();

        Set<String> uniqueExceptions = lines.stream()
                .filter(line -> line.contains("Exception") || line.contains("Error"))
                .collect(Collectors.toSet());

        return Map.of(
                "totalErrors", errorCount,
                "uniqueExceptionCount", uniqueExceptions.size(),
                "uniqueExceptions", uniqueExceptions
        );
    }

    // ---------------- RULE-BASED SEVERITY ----------------

    private String ruleBasedSeverity(String log) {

        if (log.contains("OutOfMemoryError")) {
            return "CRITICAL";
        }

        if (log.contains("Connection refused") ||
                log.contains("CannotGetJdbcConnectionException")) {
            return "HIGH";
        }

        if (log.contains("ERROR")) {
            return "MEDIUM";
        }

        return "LOW";
    }

    // ---------------- PROMPT BUILDING ----------------

    private String buildPrompt(String logContent,
                               Map<String, Object> stats,
                               String ruleSeverity) {

        return """
                You are an expert production support engineer.

                Analyze the following filtered production logs.

                Provide output strictly in JSON format with:
                {
                  "summary": "...",
                  "rootCauses": ["..."],
                  "recommendations": ["..."],
                  "severity": "..."
                }

                Log Statistics:
                Total Errors: %s
                Unique Exception Types: %s

                Rule-based Severity Suggestion: %s

                LOG CONTENT:
                -------------------------
                %s
                """.formatted(
                stats.get("totalErrors"),
                stats.get("uniqueExceptionCount"),
                ruleSeverity,
                truncateIfNeeded(logContent)
        );
    }

    // ---------------- COST CONTROL ----------------

    private String truncateIfNeeded(String log) {
        int maxChars = 5000; // cost control
        return log.length() > maxChars
                ? log.substring(0, maxChars)
                : log;
    }

    // ---------------- STRUCTURED JSON WRAPPER ----------------

    private String buildStructuredJson(String aiResponse, String ruleSeverity) {

        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> aiJson =
                    mapper.readValue(aiResponse, Map.class);

            aiJson.put("finalSeverity", ruleSeverity);

            return mapper.writeValueAsString(aiJson);

        } catch (Exception e) {
            return """
                {
                  "error": "Failed to parse AI JSON response",
                  "rawResponse": "%s"
                }
                """.formatted(aiResponse.replace("\"", "\\\""));
        }
    }

    private String escapeJson(String input) {
        return "\"" + input.replace("\"", "\\\"") + "\"";
    }

    // ---------------- HISTORY ACCESS ----------------

    public Map<String, Map<String, Object>> getAnalysisHistory() {
        return analysisHistory;
    }
}
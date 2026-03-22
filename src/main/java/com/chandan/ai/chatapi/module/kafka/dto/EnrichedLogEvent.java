package com.chandan.ai.chatapi.module.kafka.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(description = "Enriched log event with AI analysis")
public class EnrichedLogEvent {

    private String id;
    private Instant timestamp;
    private String source;
    private String rawContent;
    private String aiSummary;
    private String aiSeverity;
    private List<String> aiRecommendations;

    public EnrichedLogEvent() {
    }

    public EnrichedLogEvent(LogEventPayload source, String aiSummary, String aiSeverity, List<String> aiRecommendations) {
        this.id = source.getId();
        this.timestamp = source.getTimestamp();
        this.source = source.getSource();
        this.rawContent = source.getRawContent();
        this.aiSummary = aiSummary;
        this.aiSeverity = aiSeverity;
        this.aiRecommendations = aiRecommendations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getAiSummary() {
        return aiSummary;
    }

    public void setAiSummary(String aiSummary) {
        this.aiSummary = aiSummary;
    }

    public String getAiSeverity() {
        return aiSeverity;
    }

    public void setAiSeverity(String aiSeverity) {
        this.aiSeverity = aiSeverity;
    }

    public List<String> getAiRecommendations() {
        return aiRecommendations;
    }

    public void setAiRecommendations(List<String> aiRecommendations) {
        this.aiRecommendations = aiRecommendations;
    }
}

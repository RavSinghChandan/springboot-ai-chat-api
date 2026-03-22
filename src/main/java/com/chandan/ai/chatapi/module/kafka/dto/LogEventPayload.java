package com.chandan.ai.chatapi.module.kafka.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Log event payload for Kafka ingestion")
public class LogEventPayload {

    private String id;
    private Instant timestamp;
    private String source;
    private String rawContent;

    public LogEventPayload() {
    }

    public LogEventPayload(String id, Instant timestamp, String source, String rawContent) {
        this.id = id;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
        this.source = source;
        this.rawContent = rawContent;
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
}

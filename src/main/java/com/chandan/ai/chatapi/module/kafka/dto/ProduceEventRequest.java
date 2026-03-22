package com.chandan.ai.chatapi.module.kafka.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to produce a log event to Kafka")
public class ProduceEventRequest {

    @Schema(description = "Log content", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-01-15 ERROR Connection refused to database")
    @NotBlank(message = "Raw content cannot be empty")
    private String rawContent;

    @Schema(description = "Event source (e.g. app name, service)", example = "payment-service")
    private String source = "api";

    public ProduceEventRequest() {
    }

    public ProduceEventRequest(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

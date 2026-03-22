package com.chandan.ai.chatapi.module.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Text summarization response")
public class SummarizeResponse {

    @Schema(description = "AI-generated summary")
    private String summary;

    public SummarizeResponse() {
    }

    public SummarizeResponse(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

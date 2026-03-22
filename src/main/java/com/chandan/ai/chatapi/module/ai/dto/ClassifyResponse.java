package com.chandan.ai.chatapi.module.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Text classification response")
public class ClassifyResponse {

    @Schema(description = "Assigned category")
    private String category;

    @Schema(description = "Optional confidence or reasoning")
    private String confidence;

    public ClassifyResponse() {
    }

    public ClassifyResponse(String category) {
        this.category = category;
    }

    public ClassifyResponse(String category, String confidence) {
        this.category = category;
        this.confidence = confidence;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}

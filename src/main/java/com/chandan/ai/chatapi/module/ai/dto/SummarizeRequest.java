package com.chandan.ai.chatapi.module.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Text summarization request")
public class SummarizeRequest {

    @Schema(description = "Text to summarize", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Text cannot be empty")
    @Size(max = 10000, message = "Text cannot exceed 10000 characters")
    private String text;

    @Schema(description = "Maximum length of summary in words (optional)", example = "50")
    private Integer maxLength;

    public SummarizeRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
}

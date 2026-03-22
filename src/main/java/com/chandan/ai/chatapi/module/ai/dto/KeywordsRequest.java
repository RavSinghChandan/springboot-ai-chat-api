package com.chandan.ai.chatapi.module.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Keyword extraction request")
public class KeywordsRequest {

    @Schema(description = "Text to extract keywords from", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Text cannot be empty")
    @Size(max = 10000, message = "Text cannot exceed 10000 characters")
    private String text;

    @Schema(description = "Maximum number of keywords to extract (1-20, default 10)", example = "10")
    @Min(1)
    @Max(20)
    private Integer maxKeywords = 10;

    public KeywordsRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getMaxKeywords() {
        return maxKeywords;
    }

    public void setMaxKeywords(Integer maxKeywords) {
        this.maxKeywords = maxKeywords;
    }
}

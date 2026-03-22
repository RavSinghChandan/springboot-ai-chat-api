package com.chandan.ai.chatapi.module.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Text classification request")
public class ClassifyRequest {

    @Schema(description = "Text to classify", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Text cannot be empty")
    @Size(max = 5000, message = "Text cannot exceed 5000 characters")
    private String text;

    @Schema(description = "Optional list of categories to choose from; if omitted, AI suggests a category")
    private List<String> categories;

    public ClassifyRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}

package com.chandan.ai.chatapi.module.rag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(description = "RAG query request")
public class RagQueryRequest {

    @Schema(description = "User question", example = "What does the document say about pricing?", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Question cannot be empty")
    private String question;

    @Schema(description = "Number of similar chunks to retrieve (1-20)", example = "3", defaultValue = "3")
    @Min(1)
    @Max(20)
    private int topK = 3;

    public RagQueryRequest() {
    }

    public RagQueryRequest(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getTopK() {
        return topK;
    }

    public void setTopK(int topK) {
        this.topK = topK;
    }
}

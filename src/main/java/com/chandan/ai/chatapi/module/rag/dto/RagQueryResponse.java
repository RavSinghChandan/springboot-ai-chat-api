package com.chandan.ai.chatapi.module.rag.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "RAG query response")
public class RagQueryResponse {

    @Schema(description = "AI answer using retrieved context")
    private String answer;
    @Schema(description = "Number of document chunks used as context")
    private int sourcesUsed;

    public RagQueryResponse() {
    }

    public RagQueryResponse(String answer, int sourcesUsed) {
        this.answer = answer;
        this.sourcesUsed = sourcesUsed;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getSourcesUsed() {
        return sourcesUsed;
    }

    public void setSourcesUsed(int sourcesUsed) {
        this.sourcesUsed = sourcesUsed;
    }
}

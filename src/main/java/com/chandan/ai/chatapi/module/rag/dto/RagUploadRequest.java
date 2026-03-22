package com.chandan.ai.chatapi.module.rag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "RAG upload request - document content to index")
public class RagUploadRequest {

    @Schema(description = "Document text content", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Content cannot be empty")
    private String content;

    @Schema(description = "Optional document ID; auto-generated if omitted")
    private String documentId;

    public RagUploadRequest() {
    }

    public RagUploadRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}

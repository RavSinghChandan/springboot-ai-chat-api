package com.chandan.ai.chatapi.module.rag.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "RAG upload response")
public class RagUploadResponse {

    @Schema(description = "Document ID")
    private String documentId;
    @Schema(description = "Number of chunks stored")
    private int chunksStored;

    public RagUploadResponse() {
    }

    public RagUploadResponse(String documentId, int chunksStored) {
        this.documentId = documentId;
        this.chunksStored = chunksStored;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getChunksStored() {
        return chunksStored;
    }

    public void setChunksStored(int chunksStored) {
        this.chunksStored = chunksStored;
    }
}

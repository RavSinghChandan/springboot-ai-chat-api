package com.chandan.ai.chatapi.module.stream.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Stream request - message to stream from AI")
public class StreamRequest {

    @Schema(description = "User message", example = "Explain microservices", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 1000)
    @NotBlank(message = "Message cannot be empty")
    @Size(max = 1000, message = "Message cannot exceed 1000 characters")
    private String message;

    public StreamRequest() {
    }
    public StreamRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

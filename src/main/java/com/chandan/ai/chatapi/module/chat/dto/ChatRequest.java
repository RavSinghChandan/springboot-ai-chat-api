package com.chandan.ai.chatapi.module.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Chat request - message to send to AI")
public class ChatRequest {

    @Schema(description = "User message", example = "What is Spring Boot?", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 1000)
    @NotBlank(message = "Message cannot be empty")
    @Size(max = 1000, message = "Message cannot exceed 1000 characters")
    private String message;

    public ChatRequest() {
    }
    public ChatRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatRequest{" +
                "message='" + message + '\'' +
                '}';
    }
}

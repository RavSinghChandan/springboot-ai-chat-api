package com.chandan.ai.chatapi.module.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Chat response - AI reply")
public class ChatResponse {

    public ChatResponse() {
    }

    @Schema(description = "AI-generated reply")
    private String reply;

    public ChatResponse(String reply) {
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "ChatResponse{" +
                "reply='" + reply + '\'' +
                '}';
    }
}

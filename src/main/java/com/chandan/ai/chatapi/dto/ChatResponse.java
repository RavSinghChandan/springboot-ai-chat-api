package com.chandan.ai.chatapi.dto;

public class ChatResponse {

    public ChatResponse() {
    }

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

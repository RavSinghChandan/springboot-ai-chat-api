package com.chandan.ai.chatapi.service;

import com.chandan.ai.chatapi.client.AiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private  final AiClient aiClient;

    public ChatServiceImpl(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @Override
    public String chat(String message) {
        return aiClient.getResponse(message);
    }
}

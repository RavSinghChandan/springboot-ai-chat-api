package com.chandan.ai.chatapi.controller;


import com.chandan.ai.chatapi.dto.ChatRequest;
import com.chandan.ai.chatapi.dto.ChatResponse;
import com.chandan.ai.chatapi.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/chat")
@RestController
public class ChatController {

    @Autowired
    private  final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request){
        String reply = chatService.chat(request.getMessage());
        return new ChatResponse(reply);
    }
}

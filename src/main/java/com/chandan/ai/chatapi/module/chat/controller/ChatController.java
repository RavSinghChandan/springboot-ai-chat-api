package com.chandan.ai.chatapi.module.chat.controller;

import com.chandan.ai.chatapi.module.chat.dto.ChatRequest;
import com.chandan.ai.chatapi.module.chat.dto.ChatResponse;
import com.chandan.ai.chatapi.module.chat.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/api/chat")
@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Mono<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {

        return chatService.chat(request.getMessage())
                .map(ChatResponse::new);
    }
}

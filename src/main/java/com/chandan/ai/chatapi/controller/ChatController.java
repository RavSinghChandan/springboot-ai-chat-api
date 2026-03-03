package com.chandan.ai.chatapi.controller;

import com.chandan.ai.chatapi.dto.ChatRequest;
import com.chandan.ai.chatapi.dto.ChatResponse;
import com.chandan.ai.chatapi.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/chat")
@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // Normal Response (Project 1)
    @PostMapping
    public Mono<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {

        return chatService.chat(request.getMessage())
                .map(ChatResponse::new);
    }

    // Streaming Response (Project 2)
    @PostMapping(
            value = "/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<String> stream(@Valid @RequestBody ChatRequest request) {

        return chatService.streamChat(request.getMessage());
    }
}
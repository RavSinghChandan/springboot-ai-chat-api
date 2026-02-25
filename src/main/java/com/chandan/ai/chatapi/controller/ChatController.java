package com.chandan.ai.chatapi.controller;


import com.chandan.ai.chatapi.dto.ChatRequest;
import com.chandan.ai.chatapi.dto.ChatResponse;
import com.chandan.ai.chatapi.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

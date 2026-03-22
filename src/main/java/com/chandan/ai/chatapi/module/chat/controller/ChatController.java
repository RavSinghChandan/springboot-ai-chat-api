package com.chandan.ai.chatapi.module.chat.controller;

import com.chandan.ai.chatapi.module.chat.dto.ChatRequest;
import com.chandan.ai.chatapi.module.chat.dto.ChatResponse;
import com.chandan.ai.chatapi.module.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Chat", description = "Chat Engine - non-streaming chat responses")
@RequestMapping("/api/chat")
@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Operation(summary = "Chat", description = "Send a message and receive AI response (non-streaming)", operationId = "chat")
    @PostMapping
    public Mono<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {

        return chatService.chat(request.getMessage())
                .map(ChatResponse::new);
    }
}

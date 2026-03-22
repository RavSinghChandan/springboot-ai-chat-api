package com.chandan.ai.chatapi.module.stream.controller;

import com.chandan.ai.chatapi.module.stream.dto.StreamRequest;
import com.chandan.ai.chatapi.module.stream.service.StreamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Tag(name = "Stream", description = "Streaming Engine - real-time streaming chat responses")
@RequestMapping("/api/chat")
@RestController
public class StreamController {

    private final StreamService streamService;

    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @Operation(summary = "Stream Chat", description = "Send a message and receive AI response as Server-Sent Events stream", operationId = "stream")
    @PostMapping(
            value = "/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<String> stream(@Valid @RequestBody StreamRequest request) {

        return streamService.streamChat(request.getMessage());
    }
}

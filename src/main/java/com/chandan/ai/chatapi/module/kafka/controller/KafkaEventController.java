package com.chandan.ai.chatapi.module.kafka.controller;

import com.chandan.ai.chatapi.module.kafka.dto.LogEventPayload;
import com.chandan.ai.chatapi.module.kafka.dto.ProduceEventRequest;
import com.chandan.ai.chatapi.module.kafka.dto.ProduceEventResponse;
import com.chandan.ai.chatapi.module.kafka.service.KafkaEventProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Tag(name = "Kafka Events", description = "Event-driven AI - produce log events for async AI processing")
@RestController
@RequestMapping("/api/kafka")
@ConditionalOnProperty(name = "rav.kafka.enabled", havingValue = "true")
public class KafkaEventController {

    private final KafkaEventProducer producer;
    private final String inputTopic;

    public KafkaEventController(KafkaEventProducer producer,
                                @Value("${rav.kafka.topics.input}") String inputTopic) {
        this.producer = producer;
        this.inputTopic = inputTopic;
    }

    @Operation(summary = "Produce Event", description = "Produce a log event to Kafka for AI analysis. Event is processed async and enriched result is published to output topic.", operationId = "produceEvent")
    @PostMapping("/events")
    public Mono<ProduceEventResponse> produceEvent(@Valid @RequestBody ProduceEventRequest request) {
        LogEventPayload payload = new LogEventPayload(
                UUID.randomUUID().toString(),
                Instant.now(),
                request.getSource(),
                request.getRawContent()
        );
        String eventId = producer.send(payload);
        return Mono.just(new ProduceEventResponse(eventId, inputTopic));
    }
}

package com.chandan.ai.chatapi.module.kafka.service;

import com.chandan.ai.chatapi.core.AIOrchestratorService;
import com.chandan.ai.chatapi.module.kafka.dto.EnrichedLogEvent;
import com.chandan.ai.chatapi.module.kafka.dto.LogEventPayload;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ConditionalOnProperty(name = "rav.kafka.enabled", havingValue = "true")
public class KafkaAiProcessor {

    private static final Logger log = LoggerFactory.getLogger(KafkaAiProcessor.class);

    private final AIOrchestratorService aiOrchestrator;
    private final KafkaTemplate<String, EnrichedLogEvent> outputTemplate;
    private final ObjectMapper objectMapper;
    private final String outputTopic;

    public KafkaAiProcessor(AIOrchestratorService aiOrchestrator,
                            @Qualifier("outputKafkaTemplate") KafkaTemplate<String, EnrichedLogEvent> outputTemplate,
                            ObjectMapper objectMapper,
                            @Value("${rav.kafka.topics.output}") String outputTopic) {
        this.aiOrchestrator = aiOrchestrator;
        this.outputTemplate = outputTemplate;
        this.objectMapper = objectMapper;
        this.outputTopic = outputTopic;
    }

    @KafkaListener(topics = "${rav.kafka.topics.input}", containerFactory = "kafkaListenerContainerFactory")
    public void process(LogEventPayload event) {
        log.info("Processing log event id={}", event.getId());
        try {
            String content = event.getRawContent();
            if (content == null || content.isBlank()) {
                log.warn("Empty log content for event id={}, skipping", event.getId());
                return;
            }
            String aiResponse = aiOrchestrator.analyzeLogEvent(content).block();
            if (aiResponse != null) {
                EnrichedLogEvent enriched = parseAndEnrich(event, aiResponse);
                outputTemplate.send(outputTopic, event.getId(), enriched);
                log.info("Enriched event produced to {} for id={}", outputTopic, event.getId());
            }
        } catch (Exception e) {
            log.error("Failed to process event id={}", event.getId(), e);
        }
    }

    private EnrichedLogEvent parseAndEnrich(LogEventPayload source, String aiResponse) {
        try {
            JsonNode node = objectMapper.readTree(aiResponse);
            String summary = node.path("summary").asText("");
            String severity = node.path("severity").asText("UNKNOWN");
            List<String> recommendations = new ArrayList<>();
            node.path("recommendations").forEach(r -> recommendations.add(r.asText()));
            return new EnrichedLogEvent(source, summary, severity, recommendations);
        } catch (Exception e) {
            log.warn("Failed to parse AI JSON, using raw response. Error: {}", e.getMessage());
            return new EnrichedLogEvent(source, aiResponse, "UNKNOWN", List.of());
        }
    }
}

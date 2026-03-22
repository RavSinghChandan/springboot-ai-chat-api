package com.chandan.ai.chatapi.module.kafka.service;

import com.chandan.ai.chatapi.module.kafka.dto.LogEventPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "rav.kafka.enabled", havingValue = "true")
public class KafkaEventProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventProducer.class);

    private final KafkaTemplate<String, LogEventPayload> kafkaTemplate;
    private final String inputTopic;

    public KafkaEventProducer(KafkaTemplate<String, LogEventPayload> kafkaTemplate,
                              @Value("${rav.kafka.topics.input}") String inputTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.inputTopic = inputTopic;
    }

    public String send(LogEventPayload event) {
        String key = event.getId() != null ? event.getId() : UUID.randomUUID().toString();
        if (event.getId() == null) {
            event.setId(key);
        }
        if (event.getTimestamp() == null) {
            event.setTimestamp(Instant.now());
        }
        kafkaTemplate.send(inputTopic, key, event);
        log.info("Produced log event to {} with id={}", inputTopic, key);
        return key;
    }
}

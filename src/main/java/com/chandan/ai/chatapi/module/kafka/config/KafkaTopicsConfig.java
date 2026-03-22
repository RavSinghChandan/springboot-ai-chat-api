package com.chandan.ai.chatapi.module.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConditionalOnProperty(name = "rav.kafka.enabled", havingValue = "true")
public class KafkaTopicsConfig {

    @Value("${rav.kafka.topics.input:rav-ai.log-events.input}")
    private String inputTopic;

    @Value("${rav.kafka.topics.output:rav-ai.log-events.output}")
    private String outputTopic;

    @Bean
    public NewTopic inputTopic() {
        return TopicBuilder.name(inputTopic).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic outputTopic() {
        return TopicBuilder.name(outputTopic).partitions(3).replicas(1).build();
    }
}

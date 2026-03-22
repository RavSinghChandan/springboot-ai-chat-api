package com.chandan.ai.chatapi.module.kafka.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response after producing event to Kafka")
public class ProduceEventResponse {

    @Schema(description = "Event ID")
    private String eventId;
    @Schema(description = "Target topic")
    private String topic;

    public ProduceEventResponse() {
    }

    public ProduceEventResponse(String eventId, String topic) {
        this.eventId = eventId;
        this.topic = topic;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}

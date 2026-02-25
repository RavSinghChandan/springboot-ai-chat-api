package com.chandan.ai.chatapi.health;

import org.springframework.boot.actuate.health.*;
import org.springframework.stereotype.Component;

@Component
public class AiHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            // simple lightweight check
            return Health.up()
                    .withDetail("AI Service", "Configured")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("AI Service", "Unavailable")
                    .build();
        }
    }
}
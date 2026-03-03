package com.chandan.ai.chatapi.config;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ShutdownManager {

    private static final Logger log =
            LoggerFactory.getLogger(ShutdownManager.class);

    @PreDestroy
    public void onShutdown() {
        log.info("Application is shutting down gracefully...");
        // You can clean resources here if needed
        log.info("Cleanup complete. Shutdown finished.");
    }
}
package com.chandan.ai.chatapi.core;

import com.chandan.ai.chatapi.client.AiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Central AI service for the platform.
 * Responsibilities: Call external AI API, manage prompts, handle retries (via AiClient), track tokens.
 */
@Service
public class AIOrchestratorService {

    private static final Logger log = LoggerFactory.getLogger(AIOrchestratorService.class);

    private final AiClient aiClient;

    public AIOrchestratorService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    /**
     * Send a prompt to the AI and get a response.
     * Retries and circuit breaker are handled by AiClient.
     */
    public Mono<String> complete(String prompt) {
        log.debug("AI completion request, prompt length={}", prompt.length());
        return aiClient.getResponse(prompt)
                .doOnSuccess(r -> log.debug("AI completion succeeded, response length={}", r != null ? r.length() : 0));
    }

    /**
     * Build and send a RAG-style prompt with context.
     */
    public Mono<String> completeWithContext(String userQuestion, String context) {
        String prompt = buildRagPrompt(userQuestion, context);
        return complete(prompt);
    }

    private String buildRagPrompt(String question, String context) {
        return """
                Use the following context to answer the question. If the context does not contain relevant information, say so.

                CONTEXT:
                ---
                %s
                ---

                QUESTION: %s

                ANSWER:""".formatted(context, question);
    }

    /**
     * Summarize text using AI.
     */
    public Mono<String> summarize(String text, Integer maxLength) {
        String prompt = maxLength != null && maxLength > 0
                ? "Summarize the following text in at most %d words. Be concise.\n\nTEXT:\n%s".formatted(maxLength, text)
                : "Summarize the following text concisely.\n\nTEXT:\n%s".formatted(text);
        return complete(prompt);
    }

    /**
     * Classify text into a category. If categories provided, pick one; otherwise suggest.
     */
    public Mono<String> classify(String text, java.util.List<String> categories) {
        String prompt = categories != null && !categories.isEmpty()
                ? "Classify the following text into ONE of these categories: %s. Reply with only the category name, nothing else.\n\nTEXT:\n%s"
                        .formatted(String.join(", ", categories), text)
                : "Classify the following text. Reply with a single short category name (e.g. technical, support, feedback, bug_report).\n\nTEXT:\n%s"
                        .formatted(text);
        return complete(prompt);
    }

    /**
     * Extract keywords from text.
     */
    public Mono<String> extractKeywords(String text, int maxKeywords) {
        String prompt = "Extract up to %d key terms or phrases from the following text. Return ONLY a comma-separated list, no other text.\n\nTEXT:\n%s"
                .formatted(maxKeywords, text);
        return complete(prompt);
    }

    /**
     * Analyze log content for Kafka event processing.
     * Returns JSON with summary, severity, recommendations.
     */
    public Mono<String> analyzeLogEvent(String logContent) {
        String truncated = logContent.length() > 3000 ? logContent.substring(0, 3000) + "..." : logContent;
        String prompt = """
                You are an expert production support engineer. Analyze this log snippet.

                Provide output STRICTLY as JSON only:
                {"summary":"...","severity":"LOW|MEDIUM|HIGH|CRITICAL","recommendations":["...","..."]}

                LOG:
                ---
                %s
                ---

                JSON:""".formatted(truncated);
        return complete(prompt);
    }
}

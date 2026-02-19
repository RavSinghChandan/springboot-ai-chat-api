package com.chandan.ai.chatapi.client;

import org.springframework.web.client.RestTemplate;

public class AiClientImpl implements AiClient{

    private  final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getResponse(String prompt) {

    return  "AI response for "+ prompt;
    }
}

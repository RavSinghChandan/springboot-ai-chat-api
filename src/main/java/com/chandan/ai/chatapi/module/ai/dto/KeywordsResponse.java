package com.chandan.ai.chatapi.module.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Keyword extraction response")
public class KeywordsResponse {

    @Schema(description = "List of extracted keywords")
    private List<String> keywords;

    public KeywordsResponse() {
    }

    public KeywordsResponse(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}

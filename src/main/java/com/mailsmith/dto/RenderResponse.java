package com.mailsmith.dto;

import java.util.List;

public class RenderResponse {
    private String html;
    private List<String> errors;

    public RenderResponse(String html, List<String> errors) {
        this.html = html;
        this.errors = errors;
    }

    public String getHtml() {
        return html;
    }

    public List<String> getErrors() {
        return errors;
    }
}


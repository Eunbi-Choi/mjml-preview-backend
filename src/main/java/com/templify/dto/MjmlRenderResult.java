package com.templify.dto;

import java.util.List;

public class MjmlRenderResult {
    private final String html;
    private final List<String> errors;

    public MjmlRenderResult(String html, List<String> errors) {
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



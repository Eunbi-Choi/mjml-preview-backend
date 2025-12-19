package com.mailsmith.dto;

public class MjmlRenderResult {
    private String html;
    private String error;

    public MjmlRenderResult(String html, String error) {
        this.html = html;
        this.error = error;
    }

    // getter
}


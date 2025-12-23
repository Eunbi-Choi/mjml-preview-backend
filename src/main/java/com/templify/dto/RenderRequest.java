package com.templify.dto;

import java.util.Map;

public class RenderRequest {
    private String mjml;
    private Map<String, Object> variables;

    public String getMjml() {
        return mjml;
    }

    public void setMjml(String mjml) {
        this.mjml = mjml;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}


package com.grace.base.dto;

import lombok.Data;

@Data
public class ResultResponse {
    public ResultResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public ResultResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public ResultResponse(Object data, String code) {
        this.data = data;
        this.code = code;
    }

    private String code;
    private String message;
    private Object data;
}

package com.example.verifyrepresentative.web;

import java.io.Serializable;

public class Status implements Serializable {

    private String code;
    private String message;

    public Status() {
        this.code = "200";
        this.message = "success";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

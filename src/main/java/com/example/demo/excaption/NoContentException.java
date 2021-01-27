package com.example.demo.excaption;

public class NoContentException extends RuntimeException {
    private String errorCode = "ERR10003";

    public NoContentException(String msg) {
        super(msg);
    }

    public NoContentException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}

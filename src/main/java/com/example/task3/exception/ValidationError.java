package com.example.task3.exception;

import lombok.Data;

import java.security.PublicKey;
import java.util.List;

@Data
public class ValidationError extends RuntimeException{

    private List<?> messages;


    public ValidationError() {
    }

    public ValidationError(List<?> message){
        this.messages = message;
    }

    public ValidationError(String message) {
        super(message);
    }

    public ValidationError(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationError(Throwable cause) {
        super(cause);
    }

    public ValidationError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

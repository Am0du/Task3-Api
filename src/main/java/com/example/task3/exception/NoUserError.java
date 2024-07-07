package com.example.task3.exception;

public class NoUserError extends RuntimeException{
    public NoUserError() {
    }

    public NoUserError(String message) {
        super(message);
    }

    public NoUserError(String message, Throwable cause) {
        super(message, cause);
    }

    public NoUserError(Throwable cause) {
        super(cause);
    }

    public NoUserError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

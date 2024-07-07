package com.example.task3.exception;

public class RegistrationFailedError extends RuntimeException{

    public RegistrationFailedError() {
    }

    public RegistrationFailedError(String message) {
        super(message);
    }

    public RegistrationFailedError(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationFailedError(Throwable cause) {
        super(cause);
    }

    public RegistrationFailedError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

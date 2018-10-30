package com.mszalek.cleaningservice;

public class CleaningException extends RuntimeException {
    public CleaningException() {
        super();
    }

    public CleaningException(String message) {
        super(message);
    }

    public CleaningException(String message, Throwable cause) {
        super(message, cause);
    }

    public CleaningException(Throwable cause) {
        super(cause);
    }

    protected CleaningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

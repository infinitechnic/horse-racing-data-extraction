package com.infinitechnic.horseracing.data.hkjc.exception;

public final class ServiceFailureException extends Exception {
    public ServiceFailureException(String message) {
        super(message);
    }

    public ServiceFailureException(Throwable cause) {
        super(cause);
    }

    public ServiceFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}

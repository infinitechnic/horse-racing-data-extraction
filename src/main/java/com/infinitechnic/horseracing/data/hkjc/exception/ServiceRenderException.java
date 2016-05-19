package com.infinitechnic.horseracing.data.hkjc.exception;

public final class ServiceRenderException extends Exception {
    public ServiceRenderException(String message) {
        super(message);
    }

    public ServiceRenderException(Throwable cause) {
        super(cause);
    }

    public ServiceRenderException(String message, Throwable cause) {
        super(message, cause);
    }
}

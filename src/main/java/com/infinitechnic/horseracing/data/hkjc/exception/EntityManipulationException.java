package com.infinitechnic.horseracing.data.hkjc.exception;

public final class EntityManipulationException extends RuntimeException {
    public EntityManipulationException(String message) {
        super(message);
    }

    public EntityManipulationException(Throwable cause) {
        super(cause);
    }

    public EntityManipulationException(String message, Throwable cause) {
        super(message, cause);
    }
}

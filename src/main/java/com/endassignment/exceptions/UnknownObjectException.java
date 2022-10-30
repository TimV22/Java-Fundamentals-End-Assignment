package com.endassignment.exceptions;

public class UnknownObjectException extends RuntimeException {

    public UnknownObjectException() {
        super("Unknown object type");
    }

    public UnknownObjectException(String message) {
        super(message);
    }

    public UnknownObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownObjectException(Throwable cause) {
        super(cause);
    }
}


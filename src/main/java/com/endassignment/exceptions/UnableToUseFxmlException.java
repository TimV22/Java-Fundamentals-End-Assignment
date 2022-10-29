package com.endassignment.exceptions;

public class UnableToUseFxmlException extends RuntimeException {

    public UnableToUseFxmlException(String message) {
        super(message);
    }

    public UnableToUseFxmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToUseFxmlException(Throwable cause) {
        super(cause);
    }

}


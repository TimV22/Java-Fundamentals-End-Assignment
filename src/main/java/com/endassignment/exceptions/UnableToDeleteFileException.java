package com.endassignment.exceptions;

public class UnableToDeleteFileException extends RuntimeException {

    public UnableToDeleteFileException() {
        super("Unable to delete file");
    }
    public UnableToDeleteFileException(String message) {
        super(message);
    }

    public UnableToDeleteFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToDeleteFileException(Throwable cause) {
        super(cause);
    }
}
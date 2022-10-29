package com.endassignment.exceptions;

public class SaveDataToFileException extends RuntimeException {
    public SaveDataToFileException() {
        super("Error while saving data to file");
    }

    public SaveDataToFileException(String message) {
        super(message);
    }

    public SaveDataToFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveDataToFileException(Throwable cause) {
        super(cause);
    }
}


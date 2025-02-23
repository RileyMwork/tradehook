package com.project.exception;

public class EmailIsTakenException extends RuntimeException {
    public EmailIsTakenException(String message) {
        super(message);
    }
}

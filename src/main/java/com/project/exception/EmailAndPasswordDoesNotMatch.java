package com.project.exception;

public class EmailAndPasswordDoesNotMatch extends RuntimeException {
    public EmailAndPasswordDoesNotMatch (String message) {
        super(message);
    }
}

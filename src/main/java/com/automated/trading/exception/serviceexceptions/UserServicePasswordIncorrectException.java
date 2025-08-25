package com.automated.trading.exception.serviceexceptions;

public class UserServicePasswordIncorrectException extends RuntimeException{
    
    public UserServicePasswordIncorrectException(String message) {
        super(message);
    }

}

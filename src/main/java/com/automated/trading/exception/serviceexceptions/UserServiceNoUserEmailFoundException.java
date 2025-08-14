package com.automated.trading.exception.serviceexceptions;

public class UserServiceNoUserEmailFoundException extends RuntimeException{
    
    public UserServiceNoUserEmailFoundException(String message) {
        super(message);
    }

}

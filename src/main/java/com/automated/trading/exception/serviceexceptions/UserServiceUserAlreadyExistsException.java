package com.automated.trading.exception.serviceexceptions;

public class UserServiceUserAlreadyExistsException extends RuntimeException{

    public UserServiceUserAlreadyExistsException(String message) {
        super(message);
    }

}

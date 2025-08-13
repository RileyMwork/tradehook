package com.automated.trading.exception.daoexceptions.userdaoexceptions;

public class UpdateUserDaoNoUserFound extends RuntimeException{
    
    public UpdateUserDaoNoUserFound(String message) {
        super(message);
    }

}

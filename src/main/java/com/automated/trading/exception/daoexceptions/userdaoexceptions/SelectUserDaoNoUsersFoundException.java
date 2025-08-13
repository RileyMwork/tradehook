package com.automated.trading.exception.daoexceptions.userdaoexceptions;

public class SelectUserDaoNoUsersFoundException extends RuntimeException{
    
    public SelectUserDaoNoUsersFoundException(String message) {
        super(message);
    }

}

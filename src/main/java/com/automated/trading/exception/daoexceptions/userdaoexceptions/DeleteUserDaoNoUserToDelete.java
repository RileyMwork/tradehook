package com.automated.trading.exception.daoexceptions.userdaoexceptions;

public class DeleteUserDaoNoUserToDelete extends RuntimeException {
    
    public DeleteUserDaoNoUserToDelete(String message) {
        super(message);
    }

}

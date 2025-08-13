package com.automated.trading.exception.daoexceptions.orderdaoexceptions;

public class SelectOrderDaoNoOrderFoundException extends RuntimeException {

    public SelectOrderDaoNoOrderFoundException(String message) {
        super(message);
    }
    
}

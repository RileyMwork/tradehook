package com.automated.trading.exception.daoexceptions.orderdaoexceptions;

public class DeleteOrderDaoNoOrderToDeleteException extends RuntimeException {
    public DeleteOrderDaoNoOrderToDeleteException(String message) {
        super(message);
    }
}

package com.project.exception;

public class EmailOrPasswordTooLongException extends RuntimeException{
    public EmailOrPasswordTooLongException(String message){
        super(message); 
    }
} 
    


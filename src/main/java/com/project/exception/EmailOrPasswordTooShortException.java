package com.project.exception;

public class EmailOrPasswordTooShortException extends RuntimeException{
    public EmailOrPasswordTooShortException(String message){
        super(message); 
    }
} 
    


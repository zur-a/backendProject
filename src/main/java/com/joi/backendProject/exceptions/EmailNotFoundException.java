package com.joi.backendProject.exceptions;

public class EmailNotFoundException extends IllegalStateException{
    public EmailNotFoundException(String message) {
        super(message);
    }
}

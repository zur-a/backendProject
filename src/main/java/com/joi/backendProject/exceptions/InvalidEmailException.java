package com.joi.backendProject.exceptions;

public class InvalidEmailException extends IllegalStateException {
    public InvalidEmailException(String message) {
        super(message);
    }
}

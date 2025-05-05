package com.invetronix.backend.APIusers.exception;
 
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
} 
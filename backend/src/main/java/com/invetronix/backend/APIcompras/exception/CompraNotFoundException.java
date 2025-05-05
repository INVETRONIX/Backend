package com.invetronix.backend.APIcompras.exception;
 
public class CompraNotFoundException extends RuntimeException {
    public CompraNotFoundException(String message) {
        super(message);
    }
} 
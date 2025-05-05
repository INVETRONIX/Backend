package com.invetronix.backend.APIproducts.exception;
 
public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(String message) {
        super(message);
    }
} 
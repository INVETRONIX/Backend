package com.invetronix.backend.APIproducts.exceptions;

public class ProductAlreadyRegisteredException extends RuntimeException{
    public ProductAlreadyRegisteredException(String message){
        super(message);
    }
}
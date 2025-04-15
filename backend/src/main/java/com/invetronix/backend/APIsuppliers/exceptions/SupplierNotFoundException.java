package com.invetronix.backend.APIsuppliers.exceptions;

public class SupplierNotFoundException extends RuntimeException{
    public SupplierNotFoundException(String message){
        super(message);
    }
}

package com.invetronix.backend.APIsuppliers.exceptions;

public class SupplierAlreadyRegisteredException extends RuntimeException{
    public SupplierAlreadyRegisteredException(String message){
        super(message);
    }
}

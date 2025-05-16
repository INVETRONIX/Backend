package com.invetronix.backend.APIproductos.exception;

public class ProductoNoEncontrado extends RuntimeException{
    public ProductoNoEncontrado(String message){
        super(message);
    }
}

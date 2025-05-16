package com.invetronix.backend.APIusuarios.exception;

public class UsuarioYaRegistrado extends RuntimeException{
    public UsuarioYaRegistrado(String message){
        super(message);
    }
}
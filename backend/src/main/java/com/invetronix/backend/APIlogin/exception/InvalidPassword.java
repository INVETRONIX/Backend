package com.invetronix.backend.APIlogin.exception;

public class InvalidPassword extends RuntimeException{
    public InvalidPassword(String message){
        super(message);
    }
}

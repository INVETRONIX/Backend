package com.invetronix.backend.registroUsuario.controllers;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private String message;
    private String status;

    public ErrorResponse(String message, HttpStatus status){
        this.message = message;
        this.status = status.name();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}

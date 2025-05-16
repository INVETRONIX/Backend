package com.invetronix.backend.core.exception;

public class NoAdminAccessException extends RuntimeException {
    public NoAdminAccessException() {
        super("No tienes permisos de administrador");
    }
} 
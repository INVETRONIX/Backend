package com.invetronix.backend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.invetronix.backend.APIusuarios.exception.UsuarioNoEncontrado;
import com.invetronix.backend.APIusuarios.exception.UsuarioYaRegistrado;
import com.invetronix.backend.APIproductos.exception.ProductoNoEncontrado;
import com.invetronix.backend.APIcompras.exception.CompraNoEncontrada;
import com.invetronix.backend.APIlogin.exception.InvalidPassword;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsuarioNoEncontrado.class)
    public ResponseEntity<String> handleUsuarioNoEncontrado(UsuarioNoEncontrado ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioYaRegistrado.class)
    public ResponseEntity<String> handleUsuarioYaRegistrado(UsuarioYaRegistrado ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductoNoEncontrado.class)
    public ResponseEntity<String> handleProductoNoEncontrado(ProductoNoEncontrado ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompraNoEncontrada.class)
    public ResponseEntity<String> handleCompraNoEncontrada(CompraNoEncontrada ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPassword.class)
    public ResponseEntity<String> handleInvalidPassword(InvalidPassword ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoTokenException.class)
    public ResponseEntity<Map<String, String>> handleNoTokenException(NoTokenException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoAdminAccessException.class)
    public ResponseEntity<Map<String, String>> handleNoAdminAccessException(NoAdminAccessException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "No tienes permisos para acceder a este recurso");
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Token inválido o expirado");
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
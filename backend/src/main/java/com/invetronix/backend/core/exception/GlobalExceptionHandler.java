package com.invetronix.backend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.invetronix.backend.APIusuarios.exception.UsuarioNoEncontrado;
import com.invetronix.backend.APIusuarios.exception.UsuarioYaRegistrado;
import com.invetronix.backend.APIproductos.exception.ProductoNoEncontrado;
import com.invetronix.backend.APIcompras.exception.CompraNoEncontrada;

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
}
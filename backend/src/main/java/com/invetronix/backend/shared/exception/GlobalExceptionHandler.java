package com.invetronix.backend.shared.exception;

import com.invetronix.backend.APIusers.exception.DuplicateEmailException;
import com.invetronix.backend.APIusers.exception.UserNotFoundException;
import com.invetronix.backend.APIcompras.exception.CompraNotFoundException;
import com.invetronix.backend.APIdevoluciones.exception.DevolucionNotFoundException;
import com.invetronix.backend.APIproducts.exception.ProductoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<String> handleProductoNotFoundException(ProductoNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompraNotFoundException.class)
    public ResponseEntity<String> handleCompraNotFoundException(CompraNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DevolucionNotFoundException.class)
    public ResponseEntity<String> handleDevolucionNotFoundException(DevolucionNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
} 
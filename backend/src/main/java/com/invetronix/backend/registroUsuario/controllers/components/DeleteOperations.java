package com.invetronix.backend.registroUsuario.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.registroUsuario.controllers.in.IDeleteOperations;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.services.ServiceRegister;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteOperations implements IDeleteOperations{
    private final ServiceRegister serviceRegister;
    
    public ResponseEntity<?> deleteById(String id) {
        try {
            return serviceRegister.deleteById(id)
                .map(user -> new ResponseEntity<>(MapperUser.toDto(user), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: usuario no encontrado después de validación"));
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteByEmail(String email) {
        try {
            return serviceRegister.deleteByEmail(email)
            .map(user -> new ResponseEntity<>(MapperUser.toDto(user), HttpStatus.OK))
            .orElseThrow(() -> new RuntimeException("Error inesperado: usuario no encontrado después de validación"));
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

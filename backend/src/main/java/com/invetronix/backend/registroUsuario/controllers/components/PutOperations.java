package com.invetronix.backend.registroUsuario.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.registroUsuario.controllers.in.IPutOperations;
import com.invetronix.backend.registroUsuario.dto.DtoClient;
import com.invetronix.backend.registroUsuario.exceptions.UserAlreadyRegisteredException;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.services.ServiceRegister;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component("putOperationRegister")
@RequiredArgsConstructor
public class PutOperations implements IPutOperations{
    private final ServiceRegister serviceRegister;
    
    public ResponseEntity<?> updateById(String id, DtoClient userDto) {
        try {
            Client userUpdate = MapperUser.toModel(userDto);
            return serviceRegister.updateById(id, userUpdate)
                .map(user -> new ResponseEntity<>(MapperUser.toDto(user), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: usuario no encontrado después de validación"));
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (UserAlreadyRegisteredException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateByEmail(String email, DtoClient userDto) {
        try {
            Client userUpdate = MapperUser.toModel(userDto);
            return serviceRegister.updateByEmail(email, userUpdate)
            .map(user -> new ResponseEntity<>(MapperUser.toDto(user), HttpStatus.OK))
            .orElseThrow(() -> new RuntimeException("Error inesperado: usuario no encontrado después de validación"));
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (UserAlreadyRegisteredException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

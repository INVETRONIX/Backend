package com.invetronix.backend.registroUsuario.controllers.components;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.registroUsuario.controllers.ErrorResponse;
import com.invetronix.backend.registroUsuario.dto.DtoClient;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.services.ServiceRegister;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetOperations {
    private final ServiceRegister serviceRegister;

    public ResponseEntity<?> findById(String id) {
        try {
            return serviceRegister.findById(id)
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

    public ResponseEntity<?> findByEmail(String email) {
        try {
            return serviceRegister.findByEmail(email)
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

    public ResponseEntity<?> findAll() {
        try {
            List<Client> users = serviceRegister.findAll();
            List<DtoClient> usersDto = users.stream()
                    .map(MapperUser::toDto)
                    .toList();

            return usersDto.isEmpty() 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(usersDto, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND); 
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findByFilters(String name, String email) {
        try {
            List<Client> users = serviceRegister.findByFilters(name, email);
            List<DtoClient> usersDto = users.stream()
                    .map(MapperUser::toDto) 
                    .toList();

            return usersDto.isEmpty() 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(usersDto, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findByAuthToken(String token) {
        boolean isValid = serviceRegister.findByAuthToken(token);
        String message = isValid ? "Token válido" : "Token inválido";
        return new ResponseEntity<>(message, isValid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }
}

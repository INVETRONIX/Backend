package com.invetronix.backend.registroUsuario.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.registroUsuario.controllers.in.IPostOperations;
import com.invetronix.backend.registroUsuario.dto.DtoClient;
import com.invetronix.backend.registroUsuario.exceptions.UserAlreadyRegisteredException;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.services.ServiceRegister;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component("postOperationRegister")
@RequiredArgsConstructor
public class PostOperations implements IPostOperations{
    private final ServiceRegister serviceRegister;

    public ResponseEntity<?> save(DtoClient dtoUser) {
        try {
            Client user = MapperUser.toModel(dtoUser);
            Client saved = serviceRegister.save(user);
            DtoClient userSaved = MapperUser.toDto(saved);
            return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
        } catch (UserAlreadyRegisteredException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

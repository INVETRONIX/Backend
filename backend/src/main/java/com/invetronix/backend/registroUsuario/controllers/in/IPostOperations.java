package com.invetronix.backend.registroUsuario.controllers.in;

import org.springframework.http.ResponseEntity;
import com.invetronix.backend.registroUsuario.dto.DtoClient;

public interface IPostOperations {
    ResponseEntity<?> save(DtoClient dtoUser);
}
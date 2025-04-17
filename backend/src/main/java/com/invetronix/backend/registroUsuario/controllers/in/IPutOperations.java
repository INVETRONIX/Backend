package com.invetronix.backend.registroUsuario.controllers.in;

import org.springframework.http.ResponseEntity;
import com.invetronix.backend.registroUsuario.dto.DtoClient;

public interface IPutOperations {
    ResponseEntity<?> updateById(String id, DtoClient userDto);
    ResponseEntity<?> updateByEmail(String email, DtoClient userDto);
}
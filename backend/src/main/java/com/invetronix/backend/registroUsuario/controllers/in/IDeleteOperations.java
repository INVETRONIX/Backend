package com.invetronix.backend.registroUsuario.controllers.in;

import org.springframework.http.ResponseEntity;

public interface IDeleteOperations {
    ResponseEntity<?> deleteById(String id);
    ResponseEntity<?> deleteByEmail(String email);
}

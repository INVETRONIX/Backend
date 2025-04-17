package com.invetronix.backend.registroUsuario.controllers.in;

import org.springframework.http.ResponseEntity;

public interface IGetOperations {
    ResponseEntity<?> findById(String id);
    ResponseEntity<?> findByEmail(String email);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findByFilters(String name, String email);
    ResponseEntity<?> findByAuthToken(String token);
}

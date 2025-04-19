package com.invetronix.backend.APIretuns.controllers.in;

import org.springframework.http.ResponseEntity;

public interface IDeleteOperation {
    ResponseEntity<?> deleteById(String id);
}
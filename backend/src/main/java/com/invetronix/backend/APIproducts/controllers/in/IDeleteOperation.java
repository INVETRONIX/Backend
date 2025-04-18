package com.invetronix.backend.APIproducts.controllers.in;

import org.springframework.http.ResponseEntity;

public interface IDeleteOperation {
    ResponseEntity<?> deleteById(String id);
}

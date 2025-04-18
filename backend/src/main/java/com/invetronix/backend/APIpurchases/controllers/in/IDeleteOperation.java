package com.invetronix.backend.APIpurchases.controllers.in;

import org.springframework.http.ResponseEntity;

public interface IDeleteOperation {
     ResponseEntity<?> deleteById(String id);
}

package com.invetronix.backend.APIproducts.controllers.in;

import org.springframework.http.ResponseEntity;

public interface IGetOperations {
    ResponseEntity<?> findById(String id);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findByFilters(String nameProduct, String category, String nameSupplier);
}
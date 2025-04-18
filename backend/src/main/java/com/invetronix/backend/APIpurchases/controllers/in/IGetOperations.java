package com.invetronix.backend.APIpurchases.controllers.in;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.http.ResponseEntity;

public interface IGetOperations {
    ResponseEntity<?> findById(String id);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findByFilters(String nameClient, LocalDate date, LocalTime hour);
}
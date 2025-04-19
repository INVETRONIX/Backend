package com.invetronix.backend.APIretuns.controllers.in;

import org.springframework.http.ResponseEntity;
import com.invetronix.backend.APIretuns.dtos.DtoDevolution;

public interface IPostOperation {
    ResponseEntity<?> save(DtoDevolution dtoDevolution);
}
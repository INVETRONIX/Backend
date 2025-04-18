package com.invetronix.backend.APIproducts.controllers.in;

import org.springframework.http.ResponseEntity;
import com.invetronix.backend.APIproducts.dtos.DtoProduct;

public interface IPostOperation {
    ResponseEntity<?> save(DtoProduct dtoProduct);
}

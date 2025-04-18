package com.invetronix.backend.APIproducts.controllers.in;

import org.springframework.http.ResponseEntity;
import com.invetronix.backend.APIproducts.dtos.DtoProduct;

public interface IPutOperation {
     ResponseEntity<?> updateById(String id, DtoProduct dtoProduct);
}

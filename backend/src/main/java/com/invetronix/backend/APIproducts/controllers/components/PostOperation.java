package com.invetronix.backend.APIproducts.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIproducts.controllers.in.IPostOperation;
import com.invetronix.backend.APIproducts.dtos.DtoProduct;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.services.ServiceProduct;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostOperation implements IPostOperation{
    private final ServiceProduct serviceProduct;

    @Override
    public ResponseEntity<?> save(DtoProduct dtoProduct) {
        try {
            Product product = MapperProduct.toModel(dtoProduct);
            Product saved = serviceProduct.save(product);
            DtoProduct productSaved = MapperProduct.toDto(saved);
            return new ResponseEntity<>(productSaved, HttpStatus.CREATED);
    
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
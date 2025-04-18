package com.invetronix.backend.APIproducts.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIproducts.controllers.in.IPutOperation;
import com.invetronix.backend.APIproducts.dtos.DtoProduct;
import com.invetronix.backend.APIproducts.exceptions.ProductNotFoundException;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.services.ServiceProduct;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PutOperation implements IPutOperation{
    private final ServiceProduct serviceProduct;

    @Override
    public ResponseEntity<?> updateById(String id, DtoProduct dtoProduct) {
        try {
            Product productUpdate = MapperProduct.toModel(dtoProduct);
            return serviceProduct.updateById(id, productUpdate)
                .map(product -> new ResponseEntity<>(MapperProduct.toDto(product), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: producto no encontrado después de validación"));
        } catch (ProductNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

package com.invetronix.backend.APIproducts.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIproducts.controllers.in.IDeleteOperation;
import com.invetronix.backend.APIproducts.exceptions.ProductNotFoundException;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.services.ServiceProduct;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component("deleteOperationProduct")
@RequiredArgsConstructor
public class DeleteOperation implements IDeleteOperation{
    private final ServiceProduct serviceProduct;

    @Override
    public ResponseEntity<?> deleteById(String id) {
        try {
            return serviceProduct.deleteById(id)
                .map(product -> new ResponseEntity<>(MapperProduct.toDto(product), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: No se pudo eliminar el producto"));
        } catch (ProductNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

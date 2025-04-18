package com.invetronix.backend.APIpurchases.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIpurchases.controllers.in.IDeleteOperation;
import com.invetronix.backend.APIpurchases.exceptions.PurchaseNotFoundException;
import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIpurchases.services.ServicePurchase;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteOperation implements IDeleteOperation{
    private final ServicePurchase servicePurchase;

    @Override
    public ResponseEntity<?> deleteById(String id) {
       try {
            return servicePurchase.deleteById(id)
                .map(purhcase -> new ResponseEntity<>(MapperPurchase.toDto(purhcase), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: No se pudo eliminar la compra"));
        } catch (PurchaseNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

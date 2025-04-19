package com.invetronix.backend.APIpurchases.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIpurchases.controllers.in.IPutOperation;
import com.invetronix.backend.APIpurchases.dtos.DtoPurchase;
import com.invetronix.backend.APIpurchases.exceptions.PurchaseNotFoundException;
import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.APIpurchases.services.ServicePurchase;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component("putOperationPurchase")
@RequiredArgsConstructor
public class PutOperation implements  IPutOperation{
    private final ServicePurchase servicePurchase;

    @Override
    public ResponseEntity<?> updateById(String id, DtoPurchase dtoPurchase) {
        try {
            Purchase purchaseUpdate = MapperPurchase.toModel(dtoPurchase);
            return servicePurchase.updateById(id, purchaseUpdate)
                .map(purchase -> new ResponseEntity<>(MapperPurchase.toDto(purchase), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: producto no encontrado después de validación"));
        } catch (PurchaseNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
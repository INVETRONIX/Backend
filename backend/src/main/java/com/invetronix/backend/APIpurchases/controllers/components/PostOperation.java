package com.invetronix.backend.APIpurchases.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIpurchases.controllers.in.IPostOperation;
import com.invetronix.backend.APIpurchases.dtos.DtoPurchase;
import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.APIpurchases.services.ServicePurchase;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostOperation implements IPostOperation{
    private final ServicePurchase servicePurchase;

    @Override
    public ResponseEntity<?> save(DtoPurchase dtoPurchase) {
       try {
            Purchase purchase = MapperPurchase.toModel(dtoPurchase);
            Purchase saved = servicePurchase.save(purchase);
            DtoPurchase purchaseSaved = MapperPurchase.toDto(saved);
            return new ResponseEntity<>(purchaseSaved, HttpStatus.CREATED);
    
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
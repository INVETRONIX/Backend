package com.invetronix.backend.APIpurchases.services.usecases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.invetronix.backend.APIpurchases.entities.EntityPurchase;
import com.invetronix.backend.APIpurchases.exceptions.PurchaseNotFoundException;
import com.invetronix.backend.APIpurchases.services.in.IValidateServicePurchase;

@Service
public class ValidateServicePurchase implements IValidateServicePurchase{

    @Override
    public void validatePurchaseExist(Optional<EntityPurchase> entity, String id) {
        if(!entity.isPresent()){
            throw new PurchaseNotFoundException("La compra con id: "+id+" no existe");
        }
    }
    
}
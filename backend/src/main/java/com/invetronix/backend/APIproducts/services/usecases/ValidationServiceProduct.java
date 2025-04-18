package com.invetronix.backend.APIproducts.services.usecases;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.exceptions.ProductNotFoundException;
import com.invetronix.backend.APIproducts.services.in.IValidationServiceProduct;

@Service
public class ValidationServiceProduct implements IValidationServiceProduct{

    @Override
    public void validateProductExist(Optional<EntityProduct> entity, String id) {
        if(!entity.isPresent()){
            throw new ProductNotFoundException("El producto con id: "+id+" no existe");
        }
    }
    
}
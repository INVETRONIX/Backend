package com.invetronix.backend.APIproducts.services.in;

import java.util.Optional;
import com.invetronix.backend.APIproducts.entities.EntityProduct;

public interface IValidationServiceProduct {
    void validateProductExist(Optional<EntityProduct> entity, String id);
}
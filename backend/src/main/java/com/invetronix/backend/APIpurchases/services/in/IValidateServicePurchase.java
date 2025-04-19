package com.invetronix.backend.APIpurchases.services.in;

import java.util.Optional;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;

public interface IValidateServicePurchase {
    void validatePurchaseExist(Optional<EntityPurchase> entity, String id);
}
package com.invetronix.backend.APIpurchases.services.usecases;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;
import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.APIpurchases.repositories.RepositoryPurchase;
import com.invetronix.backend.APIpurchases.services.in.IFindPurchaseById;
import com.invetronix.backend.APIpurchases.services.in.IValidateServicePurchase;

public class FindPurchaseById implements IFindPurchaseById{
    private final RepositoryPurchase repositoryPurchase;
    private final IValidateServicePurchase validateServicePurchase;
    
    @Autowired
    public FindPurchaseById(RepositoryPurchase repositoryPurchase, IValidateServicePurchase validateServicePurchase) {
        this.repositoryPurchase = repositoryPurchase;
        this.validateServicePurchase = validateServicePurchase;
    }

    @Override
    public Optional<Purchase> findById(String id) {
        Optional<EntityPurchase> entity = repositoryPurchase.findById(id);
        validateServicePurchase.validatePurchaseExist(entity, id);
        return Optional.of(MapperPurchase.toModel(entity.get()));
    }
    
}

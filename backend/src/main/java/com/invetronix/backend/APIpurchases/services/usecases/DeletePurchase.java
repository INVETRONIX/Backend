package com.invetronix.backend.APIpurchases.services.usecases;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;
import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.APIpurchases.repositories.RepositoryPurchase;
import com.invetronix.backend.APIpurchases.services.in.IDeletePurchase;
import com.invetronix.backend.APIpurchases.services.in.IValidateServicePurchase;

public class DeletePurchase implements IDeletePurchase{
    private final RepositoryPurchase repositoryPurchase;
    private final IValidateServicePurchase validateServicePurchase;
    
    @Autowired
    public DeletePurchase(RepositoryPurchase repositoryPurchase, IValidateServicePurchase validateServicePurchase) {
        this.repositoryPurchase = repositoryPurchase;
        this.validateServicePurchase = validateServicePurchase;
    }

    @Override
    public Optional<Purchase> deleteById(String id) {
        Optional<EntityPurchase> entity = repositoryPurchase.findById(id);
        validateServicePurchase.validatePurchaseExist(entity, id);
        repositoryPurchase.delete(entity.get().getId());
        return Optional.of(MapperPurchase.toModel(entity.get()));
    }
    
}
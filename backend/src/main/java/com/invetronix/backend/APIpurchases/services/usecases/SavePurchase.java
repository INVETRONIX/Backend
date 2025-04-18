package com.invetronix.backend.APIpurchases.services.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;
import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.APIpurchases.repositories.RepositoryPurchase;
import com.invetronix.backend.APIpurchases.services.in.ISavePurchase;

public class SavePurchase implements ISavePurchase{
    private final RepositoryPurchase repositoryPurchase;

    @Autowired
    public SavePurchase(RepositoryPurchase repositoryPurchase) {
        this.repositoryPurchase = repositoryPurchase;
    }

    @Override
    public Purchase save(Purchase purchase) {
        purchase.getClient().setName(purchase.getClient().getName().toLowerCase());
        EntityPurchase entityPurchase = MapperPurchase.toEntity(purchase);
        return MapperPurchase.toModel(repositoryPurchase.save(entityPurchase));
    }
    
}
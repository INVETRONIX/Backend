package com.invetronix.backend.APIpurchases.services.usecases;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;
import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.APIpurchases.repositories.RepositoryPurchase;
import com.invetronix.backend.APIpurchases.services.in.IUpdatePurchase;
import com.invetronix.backend.APIpurchases.services.in.IValidateServicePurchase;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;

public class UpdatePurchase implements IUpdatePurchase{
    private final RepositoryPurchase repositoryPurchase;
    private final IValidateServicePurchase validateServicePurchase;

    @Autowired
    public UpdatePurchase(RepositoryPurchase repositoryPurchase, IValidateServicePurchase validateServicePurchase) {
        this.repositoryPurchase = repositoryPurchase;
        this.validateServicePurchase = validateServicePurchase;
    }

    @Override
    public Optional<Purchase> updateById(String id, Purchase purch) {
        Optional<EntityPurchase> purchase = repositoryPurchase.findById(id);
        validateServicePurchase.validatePurchaseExist(purchase, id);

        EntityPurchase entityPurchase = purchase.get();
        entityPurchase.setTotal(purch.getTotal());
        entityPurchase.setDate(purch.getDate());
        entityPurchase.setHour(purch.getHour());
        entityPurchase.setProducts(MapperProduct.toEntityFromModel(purch.getProducts()));
        entityPurchase.setClient(MapperUser.toEntity(purch.getClient()));

        return repositoryPurchase.update(entityPurchase).map(MapperPurchase::toModel);
    }
    
}

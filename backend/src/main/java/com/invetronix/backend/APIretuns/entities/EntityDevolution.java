package com.invetronix.backend.APIretuns.entities;

import java.util.UUID;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;
import lombok.Data;

@Data
public class EntityDevolution {
    private String id;
    private EntityPurchase purchase;

    public EntityDevolution(EntityPurchase purchase) {
        this.id = UUID.randomUUID().toString();
        this.purchase = purchase;
    }

}
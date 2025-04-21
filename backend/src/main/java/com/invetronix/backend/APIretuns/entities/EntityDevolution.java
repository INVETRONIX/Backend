package com.invetronix.backend.APIretuns.entities;

import java.io.Serializable;
import java.util.UUID;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;
import lombok.Data;

@Data
public class EntityDevolution implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private EntityPurchase purchase;

    public EntityDevolution(EntityPurchase purchase) {
        this.id = UUID.randomUUID().toString();
        this.purchase = purchase;
    }

}
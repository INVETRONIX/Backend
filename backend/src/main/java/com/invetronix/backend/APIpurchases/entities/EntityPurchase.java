package com.invetronix.backend.APIpurchases.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import lombok.Data;

@Data
public class EntityPurchase implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private EntityClient client;
    private String hour;
    private String date;
    private List<EntityProduct> products;
    private double total;

    public EntityPurchase(EntityClient client,String hour, String date, List<EntityProduct> products,
            double total) {
        this.id = UUID.randomUUID().toString();
        this.client = client;
        this.hour =hour;
        this.date =date;
        this.products = products;
        this.total = total;
    }

}
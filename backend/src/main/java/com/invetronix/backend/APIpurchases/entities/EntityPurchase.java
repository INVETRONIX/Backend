package com.invetronix.backend.APIpurchases.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private LocalTime hour;
    private LocalDate date;
    private List<EntityProduct> products;
    private double total;

    public EntityPurchase(EntityClient client, List<EntityProduct> products,
            double total) {
        this.id = UUID.randomUUID().toString();
        this.client = client;
        this.hour = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.date = LocalDate.now();
        this.products = products;
        this.total = total;
    }

}
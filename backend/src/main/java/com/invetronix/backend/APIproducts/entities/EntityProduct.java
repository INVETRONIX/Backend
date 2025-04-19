package com.invetronix.backend.APIproducts.entities;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class EntityProduct implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private String supplier;

    public EntityProduct(String name, String description, double price, String category, int stockQuantity,
            String supplier) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.supplier = supplier;
    }

}
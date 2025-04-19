package com.invetronix.backend.APIproducts.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Product {

    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private String supplier;

}
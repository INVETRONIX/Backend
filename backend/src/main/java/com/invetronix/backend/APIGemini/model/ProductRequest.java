package com.invetronix.backend.APIGemini.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private String supplier;   
}

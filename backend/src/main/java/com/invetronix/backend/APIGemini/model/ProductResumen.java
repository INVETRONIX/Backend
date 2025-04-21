package com.invetronix.backend.APIGemini.model;

import com.invetronix.backend.APIproducts.models.Product;
import lombok.Data;

@Data
public class ProductResumen {
    private Product product;
    private int ventasTotales;
}

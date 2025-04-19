package com.invetronix.backend.APIproducts.services.in;

import java.util.Optional;
import com.invetronix.backend.APIproducts.models.Product;

public interface IUpdateProduct {
    Optional<Product> updateById(String id, Product Product);
} 
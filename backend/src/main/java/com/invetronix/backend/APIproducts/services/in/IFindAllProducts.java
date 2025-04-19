package com.invetronix.backend.APIproducts.services.in;

import java.util.List;
import com.invetronix.backend.APIproducts.models.Product;

public interface IFindAllProducts {
    List<Product> findAll();
} 
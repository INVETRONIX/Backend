package com.invetronix.backend.APIproducts.services.in;

import java.util.Optional;
import com.invetronix.backend.APIproducts.models.Product;

public interface IFindProductById {
    Optional<Product> findById(String id);
}
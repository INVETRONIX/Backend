package com.invetronix.backend.APIproducts.services.in;

import java.util.List;
import com.invetronix.backend.APIproducts.models.Product;

public interface IFindProductsByFilters {
    List<Product> findByFilters(String nameProduct, String category, String nameSupplier);
}
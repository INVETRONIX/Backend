package com.invetronix.backend.APIproducts.services;

import java.util.List;
import java.util.Optional;
import com.invetronix.backend.APIproducts.models.Product;

public interface IServiceProduct {
    Product save(Product product);
    Optional<Product> findById(String id);
    List<Product> findAll();
    Optional<Product> delete(String id);
    Optional<Product> update(String id, Product product);
    List<Product> findByFilters(String nameProduct, String category, String nameSupplier);
}
package com.invetronix.backend.APIsuppliers.services;

import java.util.List;
import java.util.Optional;
import com.invetronix.backend.APIsuppliers.models.Supplier;

public interface IServiceSupplier {
    Supplier save(Supplier product);
    Optional<Supplier> findById(String id);
    List<Supplier> findAll();
    Optional<Supplier> delete(String id);
    Optional<Supplier> update(String id, Supplier product);
    //List<Product> findByFilters(String nameProduct, String category, String nameSupplier);
}
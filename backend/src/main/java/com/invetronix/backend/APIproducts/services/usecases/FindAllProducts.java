package com.invetronix.backend.APIproducts.services.usecases;

import java.util.List;
import java.util.stream.Collectors;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.repositories.RepositoryProduct;
import com.invetronix.backend.APIproducts.services.in.IFindAllProducts;

public class FindAllProducts implements IFindAllProducts{
    private final RepositoryProduct repository;

    public FindAllProducts(RepositoryProduct repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
            .map(MapperProduct::toModel)
            .collect(Collectors.toList());
    }
    
}

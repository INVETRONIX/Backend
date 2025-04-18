package com.invetronix.backend.APIproducts.services.usecases;

import org.springframework.beans.factory.annotation.Autowired;

import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.repositories.RepositoryProduct;
import com.invetronix.backend.APIproducts.services.in.ISaveProduct;

public class SaveProduct implements ISaveProduct{
    private final RepositoryProduct repositoryProduct;

    @Autowired
    public SaveProduct(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    @Override
    public Product save(Product product) {
        product.setCategory(product.getCategory().toLowerCase());
        product.setName(product.getName().toLowerCase());
        product.getSupplier().setName(product.getSupplier().getName().toLowerCase());
        EntityProduct entityProduct = MapperProduct.toEntity(product);
        return MapperProduct.toModel(repositoryProduct.save(entityProduct));
    }
    
}

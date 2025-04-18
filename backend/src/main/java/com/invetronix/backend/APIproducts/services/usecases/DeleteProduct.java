package com.invetronix.backend.APIproducts.services.usecases;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.repositories.RepositoryProduct;
import com.invetronix.backend.APIproducts.services.in.IDeleteProduct;
import com.invetronix.backend.APIproducts.services.in.IValidationServiceProduct;


public class DeleteProduct implements IDeleteProduct{
    private final RepositoryProduct repository;
    private final IValidationServiceProduct validation;

    @Autowired
    public DeleteProduct(RepositoryProduct repository, ValidationServiceProduct validation) {
        this.repository = repository;
        this.validation = validation;
    }

    @Override
    public Optional<Product> deleteById(String id) {
       Optional<EntityProduct> entity = repository.findById(id);
       validation.validateProductExist(entity, id);
       repository.delete(entity.get().getId());
       return Optional.of(MapperProduct.toModel(entity.get()));
    }
    
}

package com.invetronix.backend.APIproducts.services.usecases;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.repositories.RepositoryProduct;
import com.invetronix.backend.APIproducts.services.in.IUpdateProduct;
import com.invetronix.backend.APIproducts.services.in.IValidationServiceProduct;

public class UpdateProduct implements IUpdateProduct{
    private final RepositoryProduct repositoryProduct;
    private final IValidationServiceProduct validationServiceProduct;

    @Autowired
    public UpdateProduct(RepositoryProduct repositoryProduct, IValidationServiceProduct validationServiceProduct) {
        this.repositoryProduct = repositoryProduct;
        this.validationServiceProduct = validationServiceProduct;
    }

    @Override
    public Optional<Product> updateById(String id, Product product) {
        Optional<EntityProduct> entity = repositoryProduct.findById(id);
        validationServiceProduct.validateProductExist(entity, id);

        EntityProduct entityProduct = entity.get();
        entityProduct.setName(product.getName());
        entityProduct.setCategory(product.getCategory());
        entityProduct.setDescription(product.getDescription());
        entityProduct.setPrice(product.getPrice());
        entityProduct.setStockQuantity(product.getStockQuantity());
        entityProduct.setSupplier(product.getSupplier());

        return repositoryProduct.update(entityProduct).map(MapperProduct::toModel);
    }
    
}
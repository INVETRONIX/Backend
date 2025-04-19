package com.invetronix.backend.APIproducts.services.usecases;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.repositories.RepositoryProduct;
import com.invetronix.backend.APIproducts.services.in.IFindProductById;
import com.invetronix.backend.APIproducts.services.in.IValidationServiceProduct;

public class FindProductById implements IFindProductById{
    private final RepositoryProduct repositoryProduct;
    private final IValidationServiceProduct validationServiceProduct;

	@Override
	public Optional<Product> findById(String id) {
		Optional<EntityProduct> entity = repositoryProduct.findById(id);
        validationServiceProduct.validateProductExist(entity, id);
        return Optional.of(MapperProduct.toModel(entity.get()));
	}

    @Autowired
    public FindProductById(RepositoryProduct repositoryProduct, IValidationServiceProduct validationServiceProduct) {
        this.repositoryProduct = repositoryProduct;
        this.validationServiceProduct = validationServiceProduct;
    }
    
}
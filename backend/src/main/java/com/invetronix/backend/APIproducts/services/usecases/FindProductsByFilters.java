package com.invetronix.backend.APIproducts.services.usecases;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.repositories.RepositoryProduct;
import com.invetronix.backend.APIproducts.services.in.IFindProductsByFilters;

public class FindProductsByFilters implements IFindProductsByFilters{
    private final RepositoryProduct repositoryProduct;

    @Autowired
    public FindProductsByFilters(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    @Override
    public List<Product> findByFilters(String nameProduct, String category, String nameSupplier) {
        List<EntityProduct> entities = repositoryProduct.findByFilters(
            nameProduct !=null ? nameProduct.toLowerCase() :null, 
            category != null ? category.toLowerCase() : null, 
            nameSupplier != null ? nameSupplier.toLowerCase() : null
        );
        
        return entities.stream()
            .map(MapperProduct::toModel)
            .collect(Collectors.toList());
    }
    
}

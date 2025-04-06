package com.invetronix.backend.APIproducts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.mappers.MapperSupplier;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.repositories.RepositoryProduct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ServiceProduct implements IServiceProduct{
    private final RepositoryProduct repositoryProduct;
    private final IValidationsServiceProduct validationsServiceProduct;

    public Product save(Product product) {
        validationsServiceProduct.validarSiProductoYaExiste(product.getId());
        EntityProduct productParse=MapperProduct.toEntity(product);
        EntityProduct saved= repositoryProduct.save(productParse);
        return MapperProduct.toModel(saved);
    }

    public Optional<Product> findById(String id) {
        Optional<EntityProduct> entity = repositoryProduct.findById(id);
        validationsServiceProduct.validarProductoExiste(entity,id);
        Product product= MapperProduct.toModel(entity.get());
        return Optional.of(product);
        
    }

    public List<Product> findAll(){
        List<Product> listaRetornable= new ArrayList<>();

        for (EntityProduct product : repositoryProduct.findAll()) {
            listaRetornable.add(MapperProduct.toModel(product));
        }

        return listaRetornable;
    }

    public Optional<Product> delete(String id){
        Optional<EntityProduct> entity = repositoryProduct.findById(id);
        validationsServiceProduct.validarProductoExiste(entity, id);
        repositoryProduct.delete(id);
        return Optional.of(MapperProduct.toModel(entity.get()));
    }

    public Optional<Product> update(String id, Product product){
        Optional<EntityProduct> entity = repositoryProduct.findById(id);
        validationsServiceProduct.validarProductoExiste(entity,id);

        EntityProduct productUpdate = entity.get();

        productUpdate.setName(product.getName());
        productUpdate.setDescription(product.getDescription());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setCategory(product.getCategory());
        productUpdate.setStockQuantity(product.getStockQuantity());
        productUpdate.setSupplier(MapperSupplier.toEntity(product.getSupplier()));

        Product updated = MapperProduct.toModel(repositoryProduct.update(productUpdate).get());

        return Optional.of(updated);
    }

    public List<Product> findByFilters(String nameProduct, String category, String nameSupplier){
        List<EntityProduct> entities = repositoryProduct.findByFilters(
            nameProduct != null ? nameProduct.toLowerCase() : null,
            category != null ? category.toLowerCase() : null,
            nameSupplier != null ? nameSupplier.toLowerCase() : null
        );
    
        return entities.stream()
                .map(MapperProduct::toModel)
                .collect(Collectors.toList());
    }

}
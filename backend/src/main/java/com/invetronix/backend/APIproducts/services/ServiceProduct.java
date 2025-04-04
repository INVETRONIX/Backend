package com.invetronix.backend.APIproducts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.exceptions.ProductAlreadyRegisteredException;
import com.invetronix.backend.APIproducts.exceptions.ProductNotFoundException;
import com.invetronix.backend.APIproducts.mappers.MapperProduct;
import com.invetronix.backend.APIproducts.mappers.MapperSupplier;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.APIproducts.repositories.RepositoryProduct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ServiceProduct {
    private final RepositoryProduct repositoryProduct;

    public Product save(Product product) {
        if(repositoryProduct.findById(product.getId()).isPresent()){
            throw new ProductAlreadyRegisteredException("EL producto con id "+ product.getId() + " ya está registrado");
        }

        EntityProduct productParse=MapperProduct.toEntity(product);
        EntityProduct saved= repositoryProduct.save(productParse);
        return MapperProduct.toModel(saved);
    }

    public Optional<Product> findById(String id) {
        if(repositoryProduct.findById(id).isPresent()){
            Product product= MapperProduct.toModel(repositoryProduct.findById(id).get());
            return Optional.of(product);
        }

        throw new ProductNotFoundException("El producto con id "+ id + " no existe");
    }

    public List<Product> findAll(){
        List<Product> listaRetornable= new ArrayList<>();

        for (EntityProduct product : repositoryProduct.findAll()) {
            listaRetornable.add(MapperProduct.toModel(product));
        }

        return listaRetornable;
    }

    public void delete(String id){
        if(!repositoryProduct.findById(id).isPresent()){
            throw new ProductNotFoundException("El producto con id: " +id + "no existe");
        } 

        repositoryProduct.delete(id);
    }

    public Optional<Product> update(String id, Product product){
        if(!repositoryProduct.findById(id).isPresent()){
            throw new ProductNotFoundException("El producto con id: " +id + "no existe");
        }

        EntityProduct productUpdate = repositoryProduct.findById(id).get();

        productUpdate.setName(product.getName());
        productUpdate.setDescription(product.getDescription());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setCategory(product.getCategory());
        productUpdate.setStockQuantity(product.getStockQuantity());
        productUpdate.setSupplier(MapperSupplier.toEntity(product.getSupplier()));

        Product updated = MapperProduct.toModel(repositoryProduct.update(productUpdate).get());

        return Optional.of(updated);
    }

}
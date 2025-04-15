package com.invetronix.backend.APIproducts.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.exceptions.ProductAlreadyRegisteredException;
import com.invetronix.backend.APIproducts.repositories.RepositoryProduct;
import lombok.RequiredArgsConstructor;
import com.invetronix.backend.APIproducts.exceptions.ProductNotFoundException;

@Service
@RequiredArgsConstructor
public class ValidationsServiceProduct implements IValidationsServiceProduct{
    private final RepositoryProduct repositoryProduct;

    public void validarSiProductoYaExiste(String id){
        if(repositoryProduct.findById(id).isPresent()){
            throw new ProductAlreadyRegisteredException("EL producto con id "+ id + " ya está registrado");
        }
    }

    public void validarProductoExiste(Optional<EntityProduct> entity, String id) {
        if(!entity.isPresent()){
            throw new ProductNotFoundException("El producto con id "+ id + " no existe");

        }
    } 
}
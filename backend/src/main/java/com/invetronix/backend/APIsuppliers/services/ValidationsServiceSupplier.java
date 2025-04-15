package com.invetronix.backend.APIsuppliers.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIproducts.exceptions.ProductNotFoundException;
import com.invetronix.backend.APIsuppliers.entities.EntitySupplier;
import com.invetronix.backend.APIsuppliers.exceptions.SupplierAlreadyRegisteredException;
import com.invetronix.backend.APIsuppliers.repositories.RepositorySupplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ValidationsServiceSupplier implements IValidationsServiceSupplier{

    private final RepositorySupplier repositorySupplier;

    public void validarSiProveedorYaExiste(String id){
        if(repositorySupplier.findById(id).isPresent()){
            throw new SupplierAlreadyRegisteredException("El proveedor con id :" + id + "ya esta registrado");
        }
    }

    public void validarProveedorExiste(Optional<EntitySupplier> entity, String id) {
        if(!entity.isPresent()){
            throw new ProductNotFoundException("El proveedor con id "+ id + " no existe");

        }
    } 
}
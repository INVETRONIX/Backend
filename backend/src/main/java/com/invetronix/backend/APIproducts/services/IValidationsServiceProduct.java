package com.invetronix.backend.APIproducts.services;

import java.util.Optional;
import com.invetronix.backend.APIproducts.entities.EntityProduct;

public interface IValidationsServiceProduct {
    void validarSiProductoYaExiste(String id);
    void validarProductoExiste(Optional<EntityProduct> entity, String id);
}
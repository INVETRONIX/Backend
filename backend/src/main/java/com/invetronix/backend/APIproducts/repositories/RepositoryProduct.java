package com.invetronix.backend.APIproducts.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.invetronix.backend.APIproducts.entities.EntityProduct;
import com.invetronix.backend.APIproducts.data.Data;

@Repository
public class RepositoryProduct {
    private final Map<String, EntityProduct> db = Data.getInstance().read(); 

    //metodo para guardar un producto
    public EntityProduct save(EntityProduct product) { 
        db.put(product.getId(), product);
        Data.getInstance().write((HashMap<String, EntityProduct>) db);
        return product;
    }

    //metodo para buscar un producto por id
    public Optional<EntityProduct> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    //metodo para eliminar un producto
    public Optional<EntityProduct> delete(String id) {
        EntityProduct removedProduct = db.remove(id);
        if (removedProduct != null) {
            Data.getInstance().write((HashMap<String, EntityProduct>) db);
        }
        return Optional.ofNullable(removedProduct);
    }

    //metodo para modificar un producto
    public Optional<EntityProduct> update(EntityProduct product) {
        db.put(product.getId(), product);
        Data.getInstance().write((HashMap<String, EntityProduct>) db);
        return Optional.of(product);
    }

    //metodo para buscar todos los productos
    public List<EntityProduct> findAll() {
        return new ArrayList<>(db.values());
    }

    //metodo para buscar por filtros
    public List<EntityProduct> findByFilters(String nameProduct, String category, String nameSupplier) {
        return db.values().stream()
        .filter(u -> nameProduct ==null || u.getName().contains(nameProduct))
        .filter(u -> category == null  || u.getCategory().contains(category))
        .filter(u -> nameSupplier ==null || u.getSupplier().getName().contains(nameSupplier))
        .collect(Collectors.toList());
    }

}
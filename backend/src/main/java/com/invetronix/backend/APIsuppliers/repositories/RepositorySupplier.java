package com.invetronix.backend.APIsuppliers.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.invetronix.backend.APIsuppliers.data.Data;
import com.invetronix.backend.APIsuppliers.entities.EntitySupplier;

@Repository
public class RepositorySupplier {
     private final Map<String, EntitySupplier> db = Data.getInstance().read();

    //metodo para guardar un cliente
    public EntitySupplier save(EntitySupplier product) { 
        db.put(product.getId(), product);
        Data.getInstance().write((HashMap<String, EntitySupplier>) db);
        return product;
    }

    //metodo para buscar un cliente por id
    public Optional<EntitySupplier> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    //metodo para eliminar un cliente
    public Optional<EntitySupplier> delete(String id) {
        EntitySupplier removedProduct = db.remove(id);
        if (removedProduct != null) {
            Data.getInstance().write((HashMap<String, EntitySupplier>) db);
        }
        return Optional.ofNullable(removedProduct);
    }

    //metodo para modificar un cliente
    public Optional<EntitySupplier> update(EntitySupplier product) {
        db.put(product.getId(), product);
        Data.getInstance().write((HashMap<String, EntitySupplier>) db);
        return Optional.of(product);
    }

    //metodo para buscar todos los clientes
    public List<EntitySupplier> findAll() {
        return new ArrayList<>(db.values());
    }

    /*metodo para buscar por filtros
    public List<EntitySupplier> findByFilters(String nameProduct, String category, String nameSupplier) {
        return db.values().stream()
        .filter(u -> nameProduct ==null || u.getName().contains(nameProduct))
        .filter(u -> category == null  || u.getCategory().contains(category))
        .filter(u -> nameSupplier ==null || u.getSupplier().getName().contains(nameSupplier))
        .collect(Collectors.toList());
    }*/

}

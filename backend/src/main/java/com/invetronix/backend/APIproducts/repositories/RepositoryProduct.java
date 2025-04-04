package com.invetronix.backend.APIproducts.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.invetronix.backend.APIproducts.entities.EntityProduct;

@Repository
public class RepositoryProduct {
    private final Map<String, EntityProduct> db = new HashMap<>();

    //metodo para guardar un cliente
    public EntityProduct save(EntityProduct product) { 
        db.put(product.getId(), product);
        //Data.getInstance().write((HashMap<String, EntityProduct>) db);
        return product;
    }

    //metodo para buscar un cliente por id
    public Optional<EntityProduct> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    //metodo para eliminar un cliente
    public void delete(String id) {
        db.remove(id);
        //Data.getInstance().write((HashMap<String, EntityProduct>) db);
    }

    //metodo para modificar un cliente
    public Optional<EntityProduct> update(EntityProduct product) {
        db.put(product.getId(), product);
        //Data.getInstance().write((HashMap<String, EntityProduct>) db);
        return Optional.of(product);
    }

    //metodo para buscar todos los clientes
    public List<EntityProduct> findAll() {
        return new ArrayList<>(db.values());
    }

    /*metodo para buscar por filtros
    public List<EntityProduct> findByFilters(String name, String category, String Supplier) {
        return db.values().stream()
        .filter(u -> name ==null || u.getName().contains(name))
        .filter(u -> edad == 0  || ((Cliente) u).getEdad() == edad)
        .filter(u -> email ==null || u.getEmail().contains(email))
        .collect(Collectors.toList());
    }*/

}
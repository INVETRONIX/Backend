package com.invetronix.backend.APIpurchases.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.invetronix.backend.APIpurchases.data.Data;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;

@Repository
public class RepositoryPurchase {
    private final Map<String, EntityPurchase> db = Data.getInstance().read(); 

    //metodo para guardar una compra
    public EntityPurchase save(EntityPurchase purchase) { 
        db.put(purchase.getId(), purchase);
        Data.getInstance().write((HashMap<String, EntityPurchase>) db);
        return purchase;
    }

    //metodo para buscar una compra por id
    public Optional<EntityPurchase> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    //metodo para eliminar una compra
    public Optional<EntityPurchase> delete(String id) {
        EntityPurchase removedPurchase = db.remove(id);
        if (removedPurchase != null) {
            Data.getInstance().write((HashMap<String, EntityPurchase>) db);
        }
        return Optional.ofNullable(removedPurchase);
    }

    //metodo para modificar una compra
    public Optional<EntityPurchase> update(EntityPurchase purchase) {
        db.put(purchase.getId(), purchase);
        Data.getInstance().write((HashMap<String, EntityPurchase>) db);
        return Optional.of(purchase);
    }

    //metodo para buscar todos las compras
    public List<EntityPurchase> findAll() {
        return new ArrayList<>(db.values());
    }

    //metodo para buscar por filtros
    public List<EntityPurchase> findByFilters(String nameClient, LocalDate date, LocalTime hour) {
        return db.values().stream()
        .filter(u -> nameClient ==null || u.getClient().getName().contains(nameClient))
        .filter(u -> date == null  || u.getDate().equals(date))
        .filter(u -> hour ==null || u.getHour().equals(hour))
        .collect(Collectors.toList());
    }
}

package com.invetronix.backend.APIretuns.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.invetronix.backend.APIretuns.data.Data;
import com.invetronix.backend.APIretuns.entities.EntityDevolution;

@Repository
public class RepositoryDevolution {
    private final Map<String, EntityDevolution> db = Data.getInstance().read(); 

     //metodo para guardar una devolucion
    public EntityDevolution save(EntityDevolution devolution) { 
        db.put(devolution.getId(), devolution);
        Data.getInstance().write((HashMap<String, EntityDevolution>) db);
        return devolution;
    }

    //metodo para buscar una devolucion por id
    public Optional<EntityDevolution> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    //metodo para eliminar una devolucion
    public Optional<EntityDevolution> delete(String id) {
        EntityDevolution removedDevolution = db.remove(id);
        if (removedDevolution != null) {
            Data.getInstance().write((HashMap<String, EntityDevolution>) db);
        }
        return Optional.ofNullable(removedDevolution);
    }

    //metodo para modificar una devolucion
    public Optional<EntityDevolution> update(EntityDevolution devolution) {
        db.put(devolution.getId(), devolution);
        Data.getInstance().write((HashMap<String, EntityDevolution>) db);
        return Optional.of(devolution);
    }

    //metodo para buscar todas las devoluciones
    public List<EntityDevolution> findAll() {
        return new ArrayList<>(db.values());
    }

    //metodo para buscar por filtros
    public List<EntityDevolution> findByFilters(String nameClient, LocalDate date, LocalTime hour) {
        return db.values().stream()
        .filter(u -> nameClient ==null || u.getPurchase().getClient().getName().contains(nameClient))
        .filter(u -> date == null  || u.getPurchase().getDate().equals(date))
        .filter(u -> hour ==null || u.getPurchase().getHour().equals(hour))
        .collect(Collectors.toList());
    }
}
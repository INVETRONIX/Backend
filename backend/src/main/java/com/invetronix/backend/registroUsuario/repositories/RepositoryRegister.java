package com.invetronix.backend.registroUsuario.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RepositoryRegister {
    private final Map<String, EntityClient> db = new HashMap<>();

    public EntityClient save(EntityClient user){
        db.put(user.getId(), user);
        return user;
    }

    public Optional<EntityClient> findById(String id){
        return Optional.ofNullable(db.get(id));
    }

    public Optional<EntityClient> delete(String id) {
        EntityClient removed= db.remove(id);
        if (removed!= null) {
            //serializar
        }
        return Optional.ofNullable(removed);
    }

    public Optional<EntityClient> update(EntityClient user) {
        db.put(user.getId(), user);
        return Optional.of(user);
    } 

    public List<EntityClient> findAll() {
        return new ArrayList<>(db.values());
    }

    public List<EntityClient> findByFilters(String name, String email) {
        return db.values().stream()
        .filter(u -> name ==null || u.getName().contains(name))
        .filter(u -> email == null  || u.getEmail().contains(email))
        .collect(Collectors.toList());
    }

    public Optional<EntityClient> findByEmail(String email) {
        return db.values().stream()
            .filter(u -> email.equalsIgnoreCase(u.getEmail()))
            .findFirst();
    }

}
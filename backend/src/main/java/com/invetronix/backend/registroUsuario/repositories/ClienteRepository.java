package com.invetronix.backend.registroUsuario.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.invetronix.backend.registroUsuario.data.Data;
import com.invetronix.backend.registroUsuario.models.Cliente;
import com.invetronix.backend.registroUsuario.models.User;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {
    private final Map<String, User> db = Data.getInstance().read();

    //metodo para guardar un cliente
    public User save(User user) {
        db.put(user.getId(), user);
        Data.getInstance().write((HashMap<String, User>) db);
        return user;
    }

    //metodo para buscar un cliente por id
    public Optional<User> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    //metodo para eliminar un cliente
    public void delete(String id) {
        db.remove(id);
        Data.getInstance().write((HashMap<String, User>) db);
    }

    //metodo para modificar un cliente
    public Cliente update(Cliente cliente) {
        db.put(cliente.getId(), cliente);
        Data.getInstance().write((HashMap<String, User>) db);
        return cliente;
    }

    //metodo para buscar todos los clientes
    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }

    //metodo para buscar por filtros
    public List<User> findByFilters(String name, int edad, String email) {
        return db.values().stream()
        .filter(u -> name ==null || u.getName().contains(name))
        .filter(u -> edad == 0  || ((Cliente) u).getEdad() == edad)
        .filter(u -> email ==null || u.getEmail().contains(email))
        .collect(Collectors.toList());
    }

    //metodo para buscar por email
    public Optional<User> findByEmail(String email) {
        return db.values().stream()
            .filter(u -> email.equalsIgnoreCase(u.getEmail()))
            .findFirst();
    }

}
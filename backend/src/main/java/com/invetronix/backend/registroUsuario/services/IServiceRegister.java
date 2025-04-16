package com.invetronix.backend.registroUsuario.services;

import java.util.List;
import java.util.Optional;

import com.invetronix.backend.registroUsuario.models.Client;

public interface IServiceRegister {
    Client save(Client client);
    Optional<Client> findById(String id);
    Optional<Client> findByEmail(String email);
    List<Client> findAll();
    List<Client> findByFilters(String name, String email);
    Optional<Client> deleteById(String id);
    Optional<Client> deleteByEmail(String email);
    Optional<Client> updateById(String id, Client client);
    Optional<Client> updateByEmail(String email, Client client);
    boolean findByAuthToken(String token);
}

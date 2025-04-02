package com.invetronix.backend.registroUsuario.services;

import java.util.List;
import java.util.Optional;

import com.invetronix.backend.registroUsuario.models.User;

public interface IClienteService {
    User save(User cliente);

    Optional<User> findById(String id);

    List<User> findAll();

}
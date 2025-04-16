package com.invetronix.backend.login.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.invetronix.backend.login.exceptions.InvalidPasswordException;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceLogin implements IServiceLogin{
    private final RepositoryRegister repositoryRegister;

    @Override
    public Optional<Client> login(String email, String password) {
        Optional<EntityClient> entityClient = repositoryRegister.findByEmail(email);

        if(!entityClient.isPresent()){
            throw new UserNotFoundException("No se encontro un usuario con email" + email);
        }

        EntityClient entity = entityClient.get();

        if (!entity.getPassword().equals(password)) {
            throw new InvalidPasswordException("Contraseña incorrecta");
        }


        return Optional.of(MapperUser.toModel(entity));
    }

    
}
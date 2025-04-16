package com.invetronix.backend.registroUsuario.services.usecases;

import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.ISaveUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveUser implements ISaveUser{
    private final RepositoryRegister repositoryRegister;
    private final ValidationServiceRegister validationServiceRegister;

    @Override
    public Client save(Client client) {
        validationServiceRegister.validateUserNotExistsByEmail(client.getEmail());
        client.setName(client.getName().toLowerCase());
        EntityClient entityClient = MapperUser.toEntity(client);
        return MapperUser.toModel(repositoryRegister.save(entityClient));
    }
    
}
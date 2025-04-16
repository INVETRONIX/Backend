package com.invetronix.backend.registroUsuario.services.usecases;

import java.util.Optional;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.IFindUserById;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindUserById implements IFindUserById{
    private final RepositoryRegister repositoryRegister;
    private final ValidationServiceRegister validationServiceRegister;

    @Override
    public Optional<Client> findById(String id) {
        Optional<EntityClient> entity = repositoryRegister.findById(id);
        validationServiceRegister.validateUserExistsById(entity, id);
        return Optional.of(MapperUser.toModel(entity.get()));
    }
    
}
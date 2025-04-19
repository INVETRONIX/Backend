package com.invetronix.backend.registroUsuario.services.usecases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.IFindUserById;
import com.invetronix.backend.registroUsuario.services.in.IValidationServiceRegister;

public class FindUserById implements IFindUserById{
    private final RepositoryRegister repositoryRegister;
    private final IValidationServiceRegister validationServiceRegister;

    @Override
    public Optional<Client> findById(String id) {
        Optional<EntityClient> entity = repositoryRegister.findById(id);
        validationServiceRegister.validateUserExistsById(entity, id);
        return Optional.of(MapperUser.toModel(entity.get()));
    }

    @Autowired
    public FindUserById(RepositoryRegister repositoryRegister, IValidationServiceRegister validationServiceRegister) {
        this.repositoryRegister = repositoryRegister;
        this.validationServiceRegister = validationServiceRegister;
    }
    
}
package com.invetronix.backend.registroUsuario.services.usecases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.IFindUserByEmail;
import com.invetronix.backend.registroUsuario.services.in.IValidationServiceRegister;

public class FindUserByEmail implements IFindUserByEmail{
    private final RepositoryRegister repositoryRegister;
    private final IValidationServiceRegister validationServiceRegister;

    @Override
    public Optional<Client> findByEmail(String email) {
        Optional<EntityClient> entity = repositoryRegister.findByEmail(email);
        validationServiceRegister.validateUserExistsByEmail(entity, email);
        return Optional.of(MapperUser.toModel(entity.get()));
    }

    @Autowired
    public FindUserByEmail(RepositoryRegister repositoryRegister, IValidationServiceRegister validationServiceRegister) {
        this.repositoryRegister = repositoryRegister;
        this.validationServiceRegister = validationServiceRegister;
    }
    
    
}
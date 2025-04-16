package com.invetronix.backend.registroUsuario.services.usecases;

import java.util.Optional;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.IFindUserByEmail;

public class FindUserByEmail implements IFindUserByEmail{
    private final RepositoryRegister repositoryRegister;
    private final ValidationServiceRegister validationServiceRegister;

    @Override
    public Optional<Client> findByEmail(String email) {
        Optional<EntityClient> entity = repositoryRegister.findByEmail(email);
        validationServiceRegister.validateUserExistsByEmail(entity, email);
        return Optional.of(MapperUser.toModel(entity.get()));
    }

    public FindUserByEmail(RepositoryRegister repositoryRegister, ValidationServiceRegister validationServiceRegister) {
        this.repositoryRegister = repositoryRegister;
        this.validationServiceRegister = validationServiceRegister;
    }
    
    
}
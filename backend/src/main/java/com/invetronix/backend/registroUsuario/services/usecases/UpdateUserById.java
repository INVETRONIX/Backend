package com.invetronix.backend.registroUsuario.services.usecases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.IUpdateUserById;
import com.invetronix.backend.registroUsuario.services.in.IValidationServiceRegister;

public class UpdateUserById implements IUpdateUserById{
    private final RepositoryRegister repositoryRegister;
    private final IValidationServiceRegister validationServiceRegister;

    @Override
    public Optional<Client> updateById(String id, Client client) {
        Optional<EntityClient> entity = repositoryRegister.findById(id);
        validationServiceRegister.validateUserExistsById(entity, id);

        EntityClient entityToUpdate = entity.get();
        entityToUpdate.setName(client.getName());
        entityToUpdate.setEmail(client.getEmail());
        entityToUpdate.setPhone(client.getPhone());
        entityToUpdate.setAge(client.getAge());
        entityToUpdate.setPassword(client.getPassword());

        return repositoryRegister.update(entityToUpdate)
                .map(MapperUser::toModel);
    }
    
    @Autowired
    public UpdateUserById(RepositoryRegister repositoryRegister, IValidationServiceRegister validationServiceRegister) {
        this.repositoryRegister = repositoryRegister;
        this.validationServiceRegister = validationServiceRegister;
    }

    
    
}
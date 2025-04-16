package com.invetronix.backend.registroUsuario.services.usecases;

import java.util.Optional;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.IUpdateUserById;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUserById implements IUpdateUserById{
    private final RepositoryRegister repositoryRegister;
    private final ValidationServiceRegister validationServiceRegister;

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
    
}
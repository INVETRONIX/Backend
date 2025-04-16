package com.invetronix.backend.registroUsuario.services.usecases;

import java.util.List;
import java.util.stream.Collectors;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.IFindUsersByFilters;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindUsersByFilters implements IFindUsersByFilters{
    private final RepositoryRegister repositoryRegister;

    @Override
    public List<Client> findByFilters(String name, String email) {
         List<EntityClient> entities = repositoryRegister.findByFilters(
                name != null ? name.toLowerCase() : null,
                email != null ? email : null
        );
        return entities.stream()
                .map(MapperUser::toModel)
                .collect(Collectors.toList());
    }
    
}
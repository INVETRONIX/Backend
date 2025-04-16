package com.invetronix.backend.registroUsuario.services.usecases;

import java.util.List;
import java.util.stream.Collectors;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.IFindAllUsers;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindAllUsers implements IFindAllUsers {
    private final RepositoryRegister repositoryRegister;

    @Override
    public List<Client> findAll() {
        return repositoryRegister.findAll().stream()
                .map(MapperUser::toModel)
                .collect(Collectors.toList());
    }
    
}
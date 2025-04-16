package com.invetronix.backend.registroUsuario.services.usecases;

import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.IFindUserByAuthToken;


public class FindUserByAuthToken implements IFindUserByAuthToken{
    private final RepositoryRegister repositoryRegister;

    @Override
    public boolean findByAuthToken(String token) {
        return repositoryRegister.findByAuthToken(token);
    }

    public FindUserByAuthToken(RepositoryRegister repositoryRegister) {
        this.repositoryRegister = repositoryRegister;
    }
    
    
}
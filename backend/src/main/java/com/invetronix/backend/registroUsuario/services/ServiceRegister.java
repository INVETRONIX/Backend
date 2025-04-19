package com.invetronix.backend.registroUsuario.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.services.in.IDeleteUserByEmail;
import com.invetronix.backend.registroUsuario.services.in.IDeleteUserById;
import com.invetronix.backend.registroUsuario.services.in.IFindAllUsers;
import com.invetronix.backend.registroUsuario.services.in.IFindUserByAuthToken;
import com.invetronix.backend.registroUsuario.services.in.IFindUserByEmail;
import com.invetronix.backend.registroUsuario.services.in.IFindUserById;
import com.invetronix.backend.registroUsuario.services.in.IFindUsersByFilters;
import com.invetronix.backend.registroUsuario.services.in.ISaveUser;
import com.invetronix.backend.registroUsuario.services.in.IUpdateUserByEmail;
import com.invetronix.backend.registroUsuario.services.in.IUpdateUserById;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceRegister implements ISaveUser,
        IFindUserById, IFindUserByEmail, IDeleteUserById,
        IDeleteUserByEmail, IUpdateUserById, IUpdateUserByEmail,
        IFindAllUsers, IFindUsersByFilters, IFindUserByAuthToken
    {
        
    private final ISaveUser saveUser;
    private final IFindUserById findUserById;
    private final IFindUserByEmail findUserByEmail;
    private final IDeleteUserById deleteUserById;
    private final IDeleteUserByEmail deleteUserByEmail;
    private final IUpdateUserById updateUserById;
    private final IUpdateUserByEmail updateUserByEmail;
    private final IFindAllUsers findAllUsers;
    private final IFindUsersByFilters findUsersByFilters;
    private final IFindUserByAuthToken findUserByAuthToken;
        
        
    @Override
    public boolean findByAuthToken(String token) {
       return findUserByAuthToken.findByAuthToken(token);
    }

    @Override
    public List<Client> findByFilters(String name, String email) {
        return findUsersByFilters.findByFilters(name, email);
    }

    @Override
    public List<Client> findAll() {
        return findAllUsers.findAll();
    }

    @Override
    public Optional<Client> updateByEmail(String email, Client client) {
        return updateUserByEmail.updateByEmail(email, client);
    }

    @Override
    public Optional<Client> updateById(String id, Client client) {
        return updateUserById.updateById(id, client);
    }

    @Override
    public Optional<Client> deleteByEmail(String email) {
        return deleteUserByEmail.deleteByEmail(email);
    }

    @Override
    public Optional<Client> deleteById(String id) {
       return deleteUserById.deleteById(id);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return findUserByEmail.findByEmail(email);
    }

    @Override
    public Optional<Client> findById(String id) {
        return findUserById.findById(id);
    }

    @Override
    public Client save(Client client) {
       return saveUser.save(client);
    }
    
}
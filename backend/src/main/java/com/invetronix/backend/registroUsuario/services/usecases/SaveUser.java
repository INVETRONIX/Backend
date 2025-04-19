package com.invetronix.backend.registroUsuario.services.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.ISaveUser;
import com.invetronix.backend.registroUsuario.services.in.IValidationServiceRegister;

public class SaveUser implements ISaveUser{
    private final RepositoryRegister repositoryRegister;
    private final IValidationServiceRegister validationServiceRegister;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Client save(Client client) {
        validationServiceRegister.validateUserNotExistsByEmail(client.getEmail());
        client.setName(client.getName().toLowerCase());
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        EntityClient entityClient = MapperUser.toEntity(client);
        return MapperUser.toModel(repositoryRegister.save(entityClient));
    }

    @Autowired
    public SaveUser(RepositoryRegister repositoryRegister2, IValidationServiceRegister validationServiceRegister2, PasswordEncoder passwordEncoder2) {
        this.repositoryRegister= repositoryRegister2;
        this.validationServiceRegister= validationServiceRegister2;
        this.passwordEncoder= passwordEncoder2;
    }
    
}
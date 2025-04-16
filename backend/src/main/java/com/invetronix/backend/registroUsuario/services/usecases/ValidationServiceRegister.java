package com.invetronix.backend.registroUsuario.services.usecases;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.exceptions.UserAlreadyRegisteredException;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import com.invetronix.backend.registroUsuario.services.in.IValidationServiceRegister;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationServiceRegister implements IValidationServiceRegister {

    private final RepositoryRegister repositoryRegister;

    public void validateUserNotExistsByEmail(String email) {
        if (repositoryRegister.findByEmail(email).isPresent()) {
            throw new UserAlreadyRegisteredException("El usuario con email: " + email + " ya está registrado");
        }
    }

    public void validateUserExistsById(Optional<EntityClient> entity, String id) {
        if (!entity.isPresent()) {
            throw new UserNotFoundException("El usuario con id: " + id + " no existe");
        }
    }

    public void validateUserExistsByEmail(Optional<EntityClient> entity, String email) {
        if (!entity.isPresent()) {
            throw new UserNotFoundException("El usuario con email: " + email + " no existe");
        }
    }
}
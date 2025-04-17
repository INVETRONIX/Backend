package com.invetronix.backend.login.services;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.invetronix.backend.login.exceptions.InvalidPasswordException;
import com.invetronix.backend.registroUsuario.entities.EntityClient;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.repositories.RepositoryRegister;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceLogin implements IServiceLogin {
    private final RepositoryRegister repositoryRegister;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<Client> login(String email, String password) {
        EntityClient entityClient = findUserByEmail(email);
        validatePassword(password, entityClient.getPassword());
        return Optional.of(MapperUser.toModel(entityClient));
    }

    private EntityClient findUserByEmail(String email) {
        Optional<EntityClient> entityClient = repositoryRegister.findByEmail(email);
        if (!entityClient.isPresent()) {
            throw new UserNotFoundException("No se encontró un usuario con el email: " + email);
        }
        return entityClient.get();
    }

    private void validatePassword(String providedPassword, String storedPassword) {
        if (!passwordEncoder.matches(providedPassword, storedPassword)) {
            throw new InvalidPasswordException("Invalid password");
        }
    }
}
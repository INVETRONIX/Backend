package com.invetronix.backend.registroUsuario.services.in;

import java.util.Optional;
import com.invetronix.backend.registroUsuario.entities.EntityClient;

public interface IValidationServiceRegister {
    void validateUserNotExistsByEmail(String email);
    void validateUserExistsById(Optional<EntityClient> entity, String id);
    void validateUserExistsByEmail(Optional<EntityClient> entity, String email);
}
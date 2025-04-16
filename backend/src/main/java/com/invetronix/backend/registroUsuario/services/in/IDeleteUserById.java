package com.invetronix.backend.registroUsuario.services.in;

import java.util.Optional;
import com.invetronix.backend.registroUsuario.models.Client;

public interface IDeleteUserById {
    Optional<Client> deleteById(String id);
}
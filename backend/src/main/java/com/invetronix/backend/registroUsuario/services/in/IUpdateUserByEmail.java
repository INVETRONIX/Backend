package com.invetronix.backend.registroUsuario.services.in;

import java.util.Optional;
import com.invetronix.backend.registroUsuario.models.Client;

public interface IUpdateUserByEmail {
    Optional<Client> updateByEmail(String email, Client client);
}
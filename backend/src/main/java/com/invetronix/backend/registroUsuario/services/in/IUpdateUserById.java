package com.invetronix.backend.registroUsuario.services.in;

import java.util.Optional;
import com.invetronix.backend.registroUsuario.models.Client;

public interface IUpdateUserById {
    Optional<Client> updateById(String id, Client client);
}
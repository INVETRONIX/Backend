package com.invetronix.backend.registroUsuario.services.in;

import com.invetronix.backend.registroUsuario.models.Client;

public interface ISaveUser {
    Client save(Client client);
}
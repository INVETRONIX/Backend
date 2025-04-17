package com.invetronix.backend.login.services;

import java.util.Optional;
import com.invetronix.backend.registroUsuario.models.Client;

public interface IServiceLogin {
    Optional<Client> login (String email, String password);
}
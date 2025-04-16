package com.invetronix.backend.registroUsuario.services.in;

import java.util.Optional;
import com.invetronix.backend.registroUsuario.models.Client;

public interface IFindUserByEmail {
     Optional<Client> findByEmail(String email);
}
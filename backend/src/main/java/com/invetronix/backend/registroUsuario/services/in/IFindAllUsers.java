package com.invetronix.backend.registroUsuario.services.in;

import java.util.List;
import com.invetronix.backend.registroUsuario.models.Client;

public interface IFindAllUsers {
     List<Client> findAll();
}
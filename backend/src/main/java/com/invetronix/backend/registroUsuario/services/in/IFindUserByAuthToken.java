package com.invetronix.backend.registroUsuario.services.in;

public interface IFindUserByAuthToken {
    boolean findByAuthToken(String token);
}
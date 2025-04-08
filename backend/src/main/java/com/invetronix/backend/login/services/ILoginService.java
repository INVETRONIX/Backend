package com.invetronix.backend.login.services;

import java.util.Optional;

import com.invetronix.backend.registroUsuario.models.User;

public interface ILoginService {

    User login(User user);

    void verificarSiUsuarioExiste(String email, Optional<User> userOptional);

    void validatePassword(String password, String passwordDB);

    User validateUser(String email, String password);
}

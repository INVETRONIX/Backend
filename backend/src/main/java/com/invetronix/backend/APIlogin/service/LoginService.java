package com.invetronix.backend.APIlogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIlogin.model.LoginRequest;
import com.invetronix.backend.APIusuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService implements ILoginService{

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginRequest loginRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

}
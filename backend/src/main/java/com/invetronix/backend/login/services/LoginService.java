package com.invetronix.backend.login.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.login.exceptions.InvalidCredentialsException;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.models.User;
import com.invetronix.backend.registroUsuario.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    
    private final ClienteRepository clienteRepository;

    public User validateUser(String email, String password) {
        Optional<User> userOptional = clienteRepository.findByEmail(email);
        verificarSiUsuarioExiste(email, userOptional);
        User user = userOptional.get();
        validatePassword(password, user.getPassword());
        return user;
    }

    private void verificarSiUsuarioExiste(String email, Optional<User> userOptional){
        if(!userOptional.isPresent()){
            throw new UserNotFoundException("el usuario con email:" +email + "no existe");
        }
    }

    private void validatePassword(String password, String passwordDB){
        if(!password.equals(passwordDB)){
            throw new InvalidCredentialsException("Invalid password");
        }
    }

}
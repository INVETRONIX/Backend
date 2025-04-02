package com.invetronix.backend.registroUsuario.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.registroUsuario.exceptions.UserAlreadyRegisteredException;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.models.User;
import com.invetronix.backend.registroUsuario.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService{
    private final ClienteRepository clienteRepository;

    public User save(User user) {
        if(clienteRepository.findByEmail(user.getEmail()).isPresent()){
            throw new UserAlreadyRegisteredException("El usuario con correo electrónico "+ user.getEmail() + " ya está registrado");
        }
        return clienteRepository.save(user);
    }

    public Optional<User> findById(String id) {
        if(clienteRepository.findById(id).isPresent()){
            return Optional.of(clienteRepository.findById(id).get());
        }
        throw new UserNotFoundException("El usuario con id "+ id + " no existe");
    }

    public List<User> findAll(){
        return clienteRepository.findAll();
    }

}
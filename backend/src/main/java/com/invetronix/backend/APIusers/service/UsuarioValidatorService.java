package com.invetronix.backend.APIusers.service;

import com.invetronix.backend.APIusers.exception.DuplicateEmailException;
import com.invetronix.backend.APIusers.exception.UserNotFoundException;
import com.invetronix.backend.APIusers.model.Usuario;
import com.invetronix.backend.APIusers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioValidatorService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Inyección de dependencias para el repositorio de usuarios

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Inyección de dependencias para el encriptador de contraseñas

    public void validateUsuarioExists(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UserNotFoundException("Usuario con ID " + id + " no encontrado"); // Lanza una excepción si el usuario no existe
        }
    }

    public void validateEmailNotExists(String correo) {
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new DuplicateEmailException("El correo electrónico " + correo + " ya está registrado"); // Lanza una excepción si el correo ya existe
        }
    }

    public void validateEmailNotExistsForUpdate(String correo, Long id) {
        Usuario existingUsuario = usuarioRepository.findByCorreo(correo).stream()
                .findFirst()
                .orElse(null);
        
        if (existingUsuario != null && !existingUsuario.getId().equals(id)) {
            throw new DuplicateEmailException("El correo electrónico " + correo + " ya está registrado"); // Lanza una excepción si el correo ya existe
        }
    }

    public void encryptPassword(Usuario usuario) {
        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena())); // Encripta la contraseña
        }
    }

    public void updateUsuarioFields(Usuario existingUsuario, Usuario newUsuario) {
        existingUsuario.setNombre(newUsuario.getNombre()); // Actualiza el nombre del usuario
        existingUsuario.setEdad(newUsuario.getEdad()); // Actualiza la edad del usuario
        existingUsuario.setTelefono(newUsuario.getTelefono()); // Actualiza el teléfono del usuario
        
        if (newUsuario.getContrasena() != null && !newUsuario.getContrasena().isEmpty()) {
            existingUsuario.setContrasena(passwordEncoder.encode(newUsuario.getContrasena())); // Encripta la contraseña
        }
    }
} 
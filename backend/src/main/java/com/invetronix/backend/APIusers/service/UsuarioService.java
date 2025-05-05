package com.invetronix.backend.APIusers.service;

import com.invetronix.backend.APIusers.exception.UserNotFoundException;
import com.invetronix.backend.APIusers.model.Usuario;
import com.invetronix.backend.APIusers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Inyección de dependencias para el repositorio de usuarios

    @Autowired
    private UsuarioValidatorService validatorService; // Inyección de dependencia para la validación


    @Override
    @Transactional
    public Usuario saveUsuario(Usuario usuario) {
        validatorService.validateEmailNotExists(usuario.getCorreo()); // Valida que el correo no exista
        validatorService.encryptPassword(usuario); // Encripta la contraseña
        return usuarioRepository.save(usuario); // Guarda el usuario en la base de datos
    }

    @Override
    @Transactional
    public void deleteUsuario(Long id) {
        validatorService.validateUsuarioExists(id); // Valida que el usuario exista
        usuarioRepository.deleteById(id); // Elimina el usuario de la base de datos
    }

    @Override
    @Transactional
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        validatorService.validateUsuarioExists(id); // Valida que el usuario exista
        validatorService.validateEmailNotExistsForUpdate(usuarioDetails.getCorreo(), id); // Valida que el correo no exista para la actualización
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario con ID " + id + " no encontrado")); // Lanza una excepción si el usuario no existe
        
        validatorService.updateUsuarioFields(usuario, usuarioDetails); // Actualiza los campos del usuario
        usuario.setCorreo(usuarioDetails.getCorreo()); // Actualiza el correo del usuario
        
        return usuarioRepository.save(usuario); // Guarda el usuario en la base de datos
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll(); // Obtiene todos los usuarios de la base de datos
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario con ID " + id + " no encontrado")); // Lanza una excepción si el usuario no existe
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByFilters(String nombre, String correo, Integer edad) {
        return usuarioRepository.findByFilters(nombre, correo, edad); // Obtiene los usuarios que coinciden con los filtros
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombreContaining(nombre); // Obtiene los usuarios que coinciden con el nombre
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo); // Obtiene los usuarios que coinciden con el correo
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByEdad(Integer edad) {
        return usuarioRepository.findByEdad(edad); // Obtiene los usuarios que coinciden con la edad
    }
} 
package com.invetronix.backend.APIusuarios.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIusuarios.exception.UsuarioNoEncontrado;
import com.invetronix.backend.APIusuarios.exception.UsuarioYaRegistrado;
import com.invetronix.backend.APIusuarios.model.Usuario;
import com.invetronix.backend.APIusuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService{

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<Usuario> update(Long id, Usuario usuario) {
        if(usuarioRepository.getUsuarioById(id).isPresent()){
            Usuario updated = usuarioRepository.getUsuarioById(id).get();

            updated.setCorreo(usuario.getCorreo());
            updated.setEdad(usuario.getEdad());
            updated.setNombre(usuario.getNombre());
            updated.setContrasena(usuario.getContrasena());

            Usuario saved = usuarioRepository.save(updated);
            return Optional.of(saved);
        }else{
            throw new UsuarioNoEncontrado("El usuario con id : " +id + "No fue encontrado");
        }
    }

    @Override
    public Optional<Usuario> delete(Long id) {
        Optional<Usuario> usuario = usuarioRepository.getUsuarioById(id);
        if(usuario.isPresent()){
            usuarioRepository.delete(usuario.get());
            return usuario;
        } else {
            throw new UsuarioNoEncontrado("El usuario con id : " + id + " no fue encontrado");
        }
    }

    @Override
    public Usuario create(Usuario usuario) {
        if(usuarioRepository.getByCorreo(usuario.getCorreo()).isPresent()){
            throw new UsuarioYaRegistrado("EL usuario con email : " + usuario.getCorreo() + "Ya esta registrado");
        }
        
        
        usuario.setRol("CLIENTE");
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        Usuario respuesta = usuarioRepository.save(usuario);
        return respuesta;
    }

    @Override
    public Optional<Usuario> getUsuarioById(Long id) {
       if(usuarioRepository.getUsuarioById(id).isPresent()){
            return Optional.of(usuarioRepository.getUsuarioById(id).get());
       }else{
            throw new UsuarioNoEncontrado("El usuario con id : " +id + "No fue encontrado");
       }
    }

    @Override
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }
    
    @Override
    public List<Usuario> findByEdad(Integer edad) {
        return usuarioRepository.findByEdad(edad);
    }

    @Override
    public List<Usuario> findByFilters(String nombre, Integer edad) {
        return usuarioRepository.findByFilters(nombre, edad);
    }
    
}

package com.invetronix.backend.APIusers.service;

import java.util.List;
import com.invetronix.backend.APIusers.model.Usuario;

public interface IUsuarioService {
    List<Usuario> getAllUsuarios(); // Obtiene todos los usuarios
    Usuario getUsuarioById(Long id); // Obtiene un usuario por su ID
    Usuario saveUsuario(Usuario usuario); // Guarda un nuevo usuario
    void deleteUsuario(Long id); // Elimina un usuario por su ID
    Usuario updateUsuario(Long id, Usuario usuario); // Actualiza un usuario por su ID
    List<Usuario> findByFilters(String nombre, String correo, Integer edad); // Busca usuarios por filtros
    List<Usuario> findByNombre(String nombre); // Busca usuarios por nombre
    List<Usuario> findByCorreo(String correo); // Busca usuarios por correo
    List<Usuario> findByEdad(Integer edad); // Busca usuarios por edad
}

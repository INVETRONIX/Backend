package com.invetronix.backend.APIusuarios.service;

import java.util.List;
import java.util.Optional;
import com.invetronix.backend.APIusuarios.model.Usuario;

public interface IUsuarioService {
    Optional<Usuario> update(Long id, Usuario usuario);
    Optional<Usuario> delete(Long id);
    Usuario create( Usuario usuario);
    Optional<Usuario> getUsuarioById(Long id);

    List<Usuario> getAll();
    List<Usuario> findByNombre(String nombre);
    List<Usuario> findByEdad(Integer edad);
    List<Usuario> findByFilters(String nombre, Integer edad);
}

package com.invetronix.backend.APIusuarios.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.invetronix.backend.APIusuarios.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> getByCorreo(String correo);
    
    Optional<Usuario> getUsuarioById(Long id);
    
    @Query(value = "SELECT * FROM usuarios WHERE nombre LIKE CONCAT('%', :nombre, '%')", nativeQuery = true)
    List<Usuario> findByNombre(@Param("nombre") String nombre);
    
    @Query(value = "SELECT * FROM usuarios WHERE edad = :edad", nativeQuery = true)
    List<Usuario> findByEdad(@Param("edad") Integer edad);
    
    @Query(value = "SELECT * FROM usuarios WHERE (:nombre IS NULL OR nombre LIKE CONCAT('%', :nombre, '%')) AND (:edad IS NULL OR edad = :edad)", nativeQuery = true)
    List<Usuario> findByFilters(@Param("nombre") String nombre, @Param("edad") Integer edad);
}
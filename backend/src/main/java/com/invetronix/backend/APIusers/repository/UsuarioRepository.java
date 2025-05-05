package com.invetronix.backend.APIusers.repository;

import com.invetronix.backend.APIusers.model.Usuario;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@ReadingConverter
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNombreContaining(String nombre); // Obtiene los usuarios que coinciden con el nombre
    List<Usuario> findByCorreo(String correo); // Obtiene los usuarios que coinciden con el correo
    List<Usuario> findByEdad(Integer edad); // Obtiene los usuarios que coinciden con la edad
    boolean existsByCorreo(String correo); // Verifica si existe un usuario con el correo
    
    @Query("SELECT u FROM Usuario u WHERE (:nombre IS NULL OR u.nombre LIKE CONCAT('%', :nombre, '%')) AND (:correo IS NULL OR u.correo LIKE CONCAT('%', :correo, '%')) AND (:edad IS NULL OR u.edad = :edad)")
    List<Usuario> findByFilters(@Param("nombre") String nombre, 
                              @Param("correo") String correo, 
                              @Param("edad") Integer edad); // Obtiene los usuarios que coinciden con los filtros
} 
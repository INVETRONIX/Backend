package com.invetronix.backend.APIproducts.repository;

import com.invetronix.backend.APIproducts.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContaining(String nombre);
    List<Producto> findByCategoria(String categoria);
    
    @Query("SELECT p FROM Producto p WHERE p.proveedor LIKE %:proveedor%")
    List<Producto> findByProveedor(@Param("proveedor") String proveedor);
    
    @Query("SELECT p FROM Producto p WHERE " +
           "(:nombre IS NULL OR p.nombre LIKE CONCAT('%', :nombre, '%')) AND " +
           "(:categoria IS NULL OR p.categoria = :categoria) AND " +
           "(:proveedor IS NULL OR p.proveedor LIKE CONCAT('%', :proveedor, '%'))")
    List<Producto> findByFilters(@Param("nombre") String nombre, 
                               @Param("categoria") String categoria, 
                               @Param("proveedor") String proveedor);
} 
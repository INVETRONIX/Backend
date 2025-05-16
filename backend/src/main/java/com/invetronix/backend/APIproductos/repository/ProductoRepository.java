package com.invetronix.backend.APIproductos.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.invetronix.backend.APIproductos.model.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    Optional<Producto> getProductoById(Long id);

    @Query(value = "SELECT * FROM productos WHERE nombre LIKE CONCAT('%', :nombre, '%')", nativeQuery = true)
    List<Producto> findByNombre(@Param("nombre") String nombre);
    
    @Query(value = "SELECT * FROM productos WHERE precio = :precio", nativeQuery = true)
    List<Producto> findByPrecio(@Param("precio") double precio);

    @Query(value = "SELECT * FROM productos WHERE stock = :stock", nativeQuery = true)
    List<Producto> findByStock(@Param("stock") int stock);

    @Query(value = "SELECT * FROM productos WHERE (:nombre IS NULL OR nombre LIKE CONCAT('%', :nombre, '%')) AND (:precio IS NULL OR precio = :precio) AND (:stock IS NULL OR stock = :stock)", nativeQuery = true)
    List<Producto> findByFilters(@Param("nombre") String nombre, @Param("precio") double precio, @Param("stock") int stock);

}

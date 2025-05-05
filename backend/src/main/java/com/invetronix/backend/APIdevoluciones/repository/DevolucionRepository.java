package com.invetronix.backend.APIdevoluciones.repository;

import com.invetronix.backend.APIdevoluciones.model.Devolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {
    
    boolean existsByCompraId(Long compraId);
    
    Optional<Devolucion> findByCompraId(Long compraId);
    
    @Query("SELECT d FROM Devolucion d WHERE d.fechaDevolucion >= :fechaInicio")
    List<Devolucion> findByFechaInicio(@Param("fechaInicio") String fechaInicio);
    
    @Query("SELECT d FROM Devolucion d WHERE d.fechaDevolucion <= :fechaFin")
    List<Devolucion> findByFechaFin(@Param("fechaFin") String fechaFin);
    
    @Query("SELECT d FROM Devolucion d WHERE " +
           "(:compraId IS NULL OR d.compra.id = :compraId) AND " +
           "(:fechaInicio IS NULL OR d.fechaDevolucion >= :fechaInicio) AND " +
           "(:fechaFin IS NULL OR d.fechaDevolucion <= :fechaFin)")
    List<Devolucion> findByFilters(
            @Param("compraId") Long compraId,
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFin") String fechaFin);
}
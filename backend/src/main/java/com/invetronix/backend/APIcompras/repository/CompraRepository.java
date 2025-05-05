package com.invetronix.backend.APIcompras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.invetronix.backend.APIcompras.model.Compra;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    
    @Query("SELECT c FROM Compra c LEFT JOIN FETCH c.detalles WHERE c.id = :id")
    Optional<Compra> findByIdWithRelations(@Param("id") Long id);
    
    @Query("SELECT c FROM Compra c WHERE c.usuario.id = :usuarioId")
    List<Compra> findByUsuarioId(@Param("usuarioId") Long usuarioId);
    
    @Query("SELECT c FROM Compra c WHERE c.fechaCompra >= :fechaInicio")
    List<Compra> findByFechaInicio(@Param("fechaInicio") String fechaInicio);
    
    @Query("SELECT c FROM Compra c WHERE c.fechaCompra <= :fechaFin")
    List<Compra> findByFechaFin(@Param("fechaFin") String fechaFin);
    
    @Query("SELECT c FROM Compra c WHERE c.total >= :totalMin")
    List<Compra> findByTotalMin(@Param("totalMin") Double totalMin);
    
    @Query("SELECT c FROM Compra c WHERE c.total <= :totalMax")
    List<Compra> findByTotalMax(@Param("totalMax") Double totalMax);
    
    @Query("SELECT c FROM Compra c WHERE " +
           "(:usuarioId IS NULL OR c.usuario.id = :usuarioId) AND " +
           "(:fechaInicio IS NULL OR c.fechaCompra >= :fechaInicio) AND " +
           "(:fechaFin IS NULL OR c.fechaCompra <= :fechaFin) AND " +
           "(:totalMin IS NULL OR c.total >= :totalMin) AND " +
           "(:totalMax IS NULL OR c.total <= :totalMax)")
    List<Compra> findByFilters(
            @Param("usuarioId") Long usuarioId,
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFin") String fechaFin,
            @Param("totalMin") Double totalMin,
            @Param("totalMax") Double totalMax);

    List<Compra> findByFechaCompraAfter(LocalDate fecha);
} 
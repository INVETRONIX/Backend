package com.invetronix.backend.APIcompras.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.invetronix.backend.APIcompras.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    Optional<Compra> getCompraById(Long id);

    @Query(value = "SELECT * FROM compras WHERE fecha = :fecha", nativeQuery = true)
    List<Compra> findByFecha(@Param("fecha") LocalDate fecha);

    @Query(value = "SELECT * FROM compras WHERE usuario_id = :usuarioId", nativeQuery = true)
    List<Compra> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query(value = "SELECT * FROM compras WHERE hora = :hora", nativeQuery = true)
    List<Compra> findByHora(@Param("hora") LocalTime hora);

    @Query(value = "SELECT * FROM compras WHERE (:fecha IS NULL OR fecha = :fecha) AND (:usuarioId IS NULL OR usuario_id = :usuarioId) AND (:hora IS NULL OR hora = :hora)", nativeQuery = true)
    List<Compra> findByFilters(@Param("fecha") LocalDate fecha, @Param("usuarioId") Long usuarioId, @Param("hora") LocalTime hora);
}

package com.invetronix.backend.APIcompras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import com.invetronix.backend.APIcompras.model.Compra;

public interface ICompraService {
    Optional<Compra> update(Long id, Compra compra);
    Optional<Compra> delete(Long id);
    Compra create(Compra compra);
    Optional<Compra> getCompraById(Long id);

    List<Compra> getAll();
    List<Compra> findByFecha(LocalDate fecha);
    List<Compra> findByUsuarioId(Long usuarioId);
    List<Compra> findByHora(LocalTime hora);
    List<Compra> findByFilters(LocalDate fecha, Long usuarioId, LocalTime hora);
}

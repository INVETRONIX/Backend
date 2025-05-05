package com.invetronix.backend.APIcompras.service;

import com.invetronix.backend.APIcompras.model.Compra;
import java.util.List;

public interface ICompraService {
    List<Compra> getAllCompras();
    Compra getCompraById(Long id);
    Compra saveCompra(Compra compra);
    Compra updateCompra(Long id, Compra compra);
    void deleteCompra(Long id);
    
    // Métodos individuales de búsqueda
    List<Compra> findByUsuarioId(Long usuarioId);
    List<Compra> findByFechaInicio(String fechaInicio);
    List<Compra> findByFechaFin(String fechaFin);
    List<Compra> findByTotalMin(Double totalMin);
    List<Compra> findByTotalMax(Double totalMax);
    
    // Método de búsqueda por filtros combinados
    List<Compra> findByFilters(Long usuarioId, String fechaInicio, String fechaFin, Double totalMin, Double totalMax);
} 
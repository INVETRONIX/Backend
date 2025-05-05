package com.invetronix.backend.APIdevoluciones.service;

import com.invetronix.backend.APIdevoluciones.model.Devolucion;
import java.util.List;

public interface IDevolucionService {
    List<Devolucion> getAllDevoluciones();
    Devolucion getDevolucionById(Long id);
    Devolucion saveDevolucion(Devolucion devolucion);
    Devolucion updateDevolucion(Long id, Devolucion devolucion);
    void deleteDevolucion(Long id);
    
    // Métodos individuales de búsqueda
    Devolucion findByCompraId(Long compraId);
    List<Devolucion> findByFechaInicio(String fechaInicio);
    List<Devolucion> findByFechaFin(String fechaFin);
    
    // Método de búsqueda por filtros combinados
    List<Devolucion> findByFilters(Long compraId, String fechaInicio, String fechaFin);
} 
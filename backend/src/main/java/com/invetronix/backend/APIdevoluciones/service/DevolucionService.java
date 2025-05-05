package com.invetronix.backend.APIdevoluciones.service;

import com.invetronix.backend.APIdevoluciones.exception.DevolucionNotFoundException;
import com.invetronix.backend.APIdevoluciones.model.Devolucion;
import com.invetronix.backend.APIdevoluciones.repository.DevolucionRepository;
import com.invetronix.backend.APIcompras.exception.CompraNotFoundException;
import com.invetronix.backend.APIcompras.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DevolucionService implements IDevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Devolucion> getAllDevoluciones() {
        return devolucionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Devolucion getDevolucionById(Long id) {
        return devolucionRepository.findById(id)
                .orElseThrow(() -> new DevolucionNotFoundException("Devolución con ID " + id + " no encontrada"));
    }

    @Override
    @Transactional
    public Devolucion saveDevolucion(Devolucion devolucion) {
        // Validar que la compra existe
        if (!compraRepository.existsById(devolucion.getCompra().getId())) {
            throw new CompraNotFoundException("Compra con ID " + devolucion.getCompra().getId() + " no encontrada");
        }

        // Validar que no existe ya una devolución para esta compra
        if (devolucionRepository.existsByCompraId(devolucion.getCompra().getId())) {
            throw new IllegalStateException("Ya existe una devolución para la compra con ID " + devolucion.getCompra().getId());
        }

        return devolucionRepository.save(devolucion);
    }

    @Override
    @Transactional
    public Devolucion updateDevolucion(Long id, Devolucion devolucion) {
        Devolucion devolucionExistente = getDevolucionById(id);
        
        // Validar que la nueva compra existe
        if (!compraRepository.existsById(devolucion.getCompra().getId())) {
            throw new CompraNotFoundException("Compra con ID " + devolucion.getCompra().getId() + " no encontrada");
        }
        
        // Validar que no existe ya una devolución para la nueva compra (si es diferente)
        if (!devolucionExistente.getCompra().getId().equals(devolucion.getCompra().getId()) &&
            devolucionRepository.existsByCompraId(devolucion.getCompra().getId())) {
            throw new IllegalStateException("Ya existe una devolución para la compra con ID " + devolucion.getCompra().getId());
        }
        
        devolucionExistente.setCompra(devolucion.getCompra());
        devolucionExistente.setFechaDevolucion(devolucion.getFechaDevolucion());
        devolucionExistente.setHoraDevolucion(devolucion.getHoraDevolucion());
        
        return devolucionRepository.save(devolucionExistente);
    }

    @Override
    @Transactional
    public void deleteDevolucion(Long id) {
        Devolucion devolucion = getDevolucionById(id);
        devolucionRepository.delete(devolucion);
    }

    @Override
    @Transactional(readOnly = true)
    public Devolucion findByCompraId(Long compraId) {
        return devolucionRepository.findByCompraId(compraId)
                .orElseThrow(() -> new DevolucionNotFoundException("No se encontró devolución para la compra con ID " + compraId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Devolucion> findByFechaInicio(String fechaInicio) {
        return devolucionRepository.findByFechaInicio(fechaInicio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Devolucion> findByFechaFin(String fechaFin) {
        return devolucionRepository.findByFechaFin(fechaFin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Devolucion> findByFilters(Long compraId, String fechaInicio, String fechaFin) {
        return devolucionRepository.findByFilters(compraId, fechaInicio, fechaFin);
    }
}
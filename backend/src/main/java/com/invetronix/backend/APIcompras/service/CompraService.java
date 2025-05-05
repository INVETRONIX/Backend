package com.invetronix.backend.APIcompras.service;

import com.invetronix.backend.APIcompras.exception.CompraNotFoundException;
import com.invetronix.backend.APIcompras.model.Compra;
import com.invetronix.backend.APIcompras.model.DetalleCompra;
import com.invetronix.backend.APIcompras.repository.CompraRepository;
import com.invetronix.backend.APIcompras.repository.DetalleCompraRepository;
import com.invetronix.backend.APIproducts.exception.ProductoNotFoundException;
import com.invetronix.backend.APIproducts.model.Producto;
import com.invetronix.backend.APIproducts.repository.ProductoRepository;
import com.invetronix.backend.APIusers.exception.UserNotFoundException;
import com.invetronix.backend.APIusers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CompraService implements ICompraService {

    @Autowired
    private CompraRepository compraRepository;


    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Compra getCompraById(Long id) {
        return compraRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new CompraNotFoundException("Compra con ID " + id + " no encontrada"));
    }

    @Override
    @Transactional
    public Compra saveCompra(Compra compra) {
        // Validar que el usuario existe
        if (!usuarioRepository.existsById(compra.getUsuario().getId())) {
            throw new UserNotFoundException("Usuario con ID " + compra.getUsuario().getId() + " no encontrado");
        }

        // Validar y procesar los detalles
        if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos un detalle");
        }

        // Validar que todos los productos existen y establecer la relación
        for (DetalleCompra detalle : compra.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new ProductoNotFoundException("Producto con ID " + detalle.getProducto().getId() + " no encontrado"));
            
            detalle.setProducto(producto);
            detalle.setCompra(compra);
        }
        
        // Calcular el total usando el precio del producto
        double total = compra.getDetalles().stream()
            .mapToDouble(detalle -> detalle.getCantidad() * detalle.getProducto().getPrecio())
            .sum();
        compra.setTotal(total);

        return compraRepository.save(compra);
    }

    @Override
    @Transactional
    public Compra updateCompra(Long id, Compra compraActualizada) {
        Compra compraExistente = getCompraById(id);
        
        // Validar que el nuevo usuario existe
        if (!usuarioRepository.existsById(compraActualizada.getUsuario().getId())) {
            throw new UserNotFoundException("Usuario con ID " + compraActualizada.getUsuario().getId() + " no encontrado");
        }
        
        // Actualizar usuario
        compraExistente.setUsuario(compraActualizada.getUsuario());
        
        // Actualizar detalles si se proporcionan
        if (compraActualizada.getDetalles() != null && !compraActualizada.getDetalles().isEmpty()) {
            // Limpiar detalles existentes
            compraExistente.getDetalles().clear();
            
            // Agregar nuevos detalles
            compraActualizada.getDetalles().forEach(detalle -> {
                Producto producto = productoRepository.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new ProductoNotFoundException("Producto con ID " + detalle.getProducto().getId() + " no encontrado"));
                
                detalle.setProducto(producto);
                detalle.setCompra(compraExistente);
                compraExistente.getDetalles().add(detalle);
            });
            
            // Recalcular total usando el precio del producto
            double total = compraExistente.getDetalles().stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getProducto().getPrecio())
                .sum();
            compraExistente.setTotal(total);
        }
        
        return compraRepository.save(compraExistente);
    }

    @Override
    @Transactional
    public void deleteCompra(Long id) {
        Compra compra = compraRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new CompraNotFoundException("Compra con ID " + id + " no encontrada"));
        
        // Primero eliminamos los detalles de compra
        if (compra.getDetalles() != null) {
            for (DetalleCompra detalle : compra.getDetalles()) {
                detalleCompraRepository.delete(detalle);
            }
        }
        
        // Luego eliminamos la compra
        compraRepository.delete(compra);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> findByUsuarioId(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new UserNotFoundException("Usuario con ID " + usuarioId + " no encontrado");
        }
        return compraRepository.findByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> findByFechaInicio(String fechaInicio) {
        return compraRepository.findByFechaInicio(fechaInicio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> findByFechaFin(String fechaFin) {
        return compraRepository.findByFechaFin(fechaFin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> findByTotalMin(Double minTotal) {
        return compraRepository.findByTotalMin(minTotal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> findByTotalMax(Double maxTotal) {
        return compraRepository.findByTotalMax(maxTotal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> findByFilters(Long usuarioId, String fechaInicio, String fechaFin, Double totalMin, Double totalMax) {
        if (usuarioId != null && !usuarioRepository.existsById(usuarioId)) {
            throw new UserNotFoundException("Usuario con ID " + usuarioId + " no encontrado");
        }
        return compraRepository.findByFilters(usuarioId, fechaInicio, fechaFin, totalMin, totalMax);
    }
} 
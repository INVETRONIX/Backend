package com.invetronix.backend.APIproducts.service;

import com.invetronix.backend.APIproducts.exception.ProductoNotFoundException;
import com.invetronix.backend.APIproducts.model.Producto;
import com.invetronix.backend.APIproducts.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con ID " + id + " no encontrado"));
    }

    @Override
    @Transactional
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public Producto updateProducto(Long id, Producto productoDetails) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con ID " + id + " no encontrado"));
        
        producto.setNombre(productoDetails.getNombre());
        producto.setDescripcion(productoDetails.getDescripcion());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setCategoria(productoDetails.getCategoria());
        producto.setStock(productoDetails.getStock());
        producto.setProveedor(productoDetails.getProveedor());
        
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void deleteProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException("Producto con ID " + id + " no encontrado");
        }
        productoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombreContaining(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByProveedor(String proveedor) {
        return productoRepository.findByProveedor(proveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByFilters(String nombre, String categoria, String proveedor) {
        return productoRepository.findByFilters(nombre, categoria, proveedor);
    }
} 
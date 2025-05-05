package com.invetronix.backend.APIproducts.service;

import com.invetronix.backend.APIproducts.model.Producto;
import org.springframework.stereotype.Service;

@Service
public class ProductoValidatorService {

    public void validateProducto(Producto producto) {
        validateNombre(producto.getNombre());
        validateDescripcion(producto.getDescripcion());
        validatePrecio(producto.getPrecio());
        validateCategoria(producto.getCategoria());
        validateStock(producto.getStock());
        validateProveedor(producto.getProveedor());
    }

    private void validateNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (nombre.length() > 100) {
            throw new IllegalArgumentException("El nombre del producto no puede tener más de 100 caracteres");
        }
    }

    private void validateDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del producto no puede estar vacía");
        }
        if (descripcion.length() > 1000) {
            throw new IllegalArgumentException("La descripción del producto no puede tener más de 1000 caracteres");
        }
    }

    private void validatePrecio(Double precio) {
        if (precio == null) {
            throw new IllegalArgumentException("El precio del producto no puede ser nulo");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor que cero");
        }
    }

    private void validateCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría del producto no puede estar vacía");
        }
        if (categoria.length() > 50) {
            throw new IllegalArgumentException("La categoría del producto no puede tener más de 50 caracteres");
        }
    }

    private void validateStock(Integer stock) {
        if (stock == null) {
            throw new IllegalArgumentException("El stock del producto no puede ser nulo");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock del producto no puede ser negativo");
        }
    }

    private void validateProveedor(String proveedor) {
        if (proveedor == null || proveedor.trim().isEmpty()) {
            throw new IllegalArgumentException("El proveedor del producto no puede estar vacío");
        }
        if (proveedor.length() > 100) {
            throw new IllegalArgumentException("El proveedor del producto no puede tener más de 100 caracteres");
        }
    }
} 
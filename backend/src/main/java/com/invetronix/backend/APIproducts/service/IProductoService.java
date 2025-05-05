package com.invetronix.backend.APIproducts.service;

import java.util.List;
import com.invetronix.backend.APIproducts.model.Producto;

public interface IProductoService {
    List<Producto> getAllProductos();
    Producto getProductoById(Long id);
    Producto saveProducto(Producto producto);
    void deleteProducto(Long id);
    Producto updateProducto(Long id, Producto producto);
    List<Producto> findByFilters(String nombre, String categoria, String proveedor);
    List<Producto> findByNombre(String nombre);
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByProveedor(String proveedor);
}
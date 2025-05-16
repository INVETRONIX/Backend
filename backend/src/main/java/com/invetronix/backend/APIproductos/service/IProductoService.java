package com.invetronix.backend.APIproductos.service;

import java.util.List;
import java.util.Optional;
import com.invetronix.backend.APIproductos.model.Producto;

public interface IProductoService {
    Optional<Producto> update(Long id, Producto producto);
    Optional<Producto> delete(Long id);
    Producto create(Producto producto);
    Optional<Producto> getProductoById(Long id);
    
    List<Producto> getAll();
    List<Producto> findByNombre(String nombre);
    List<Producto> findByPrecio(double precio);
    List<Producto> findByStock(int stock);
    List<Producto> findByFilters(String nombre, Double precio, Integer stock);
}

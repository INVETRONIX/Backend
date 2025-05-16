package com.invetronix.backend.APIproductos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIproductos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import com.invetronix.backend.APIproductos.model.Producto;
import com.invetronix.backend.APIproductos.exception.ProductoNoEncontrado;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoService{

    @Autowired
    private final ProductoRepository productoRepository;

    @Override
    public Optional<Producto> update(Long id, Producto producto) {
        if(productoRepository.getProductoById(id).isPresent()){
            Producto updated = productoRepository.getProductoById(id).get();

            updated.setNombre(producto.getNombre());
            updated.setPrecio(producto.getPrecio());
            updated.setStock(producto.getStock());

            Producto saved = productoRepository.save(updated);
            return Optional.of(saved);
        }else{
            throw new ProductoNoEncontrado("El producto con id : " + id + " no fue encontrado");
        }
    }

    @Override
    public Optional<Producto> delete(Long id) {
        Optional<Producto> producto = productoRepository.getProductoById(id);
        if(producto.isPresent()){
            productoRepository.delete(producto.get());
            return producto;
        } else {
            throw new ProductoNoEncontrado("El producto con id : " + id + " no fue encontrado");
        }
    }

    @Override
    public Producto create(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> getProductoById(Long id) {
        if(productoRepository.getProductoById(id).isPresent()){
            return Optional.of(productoRepository.getProductoById(id).get());
        }else{
            throw new ProductoNoEncontrado("El producto con id : " + id + " no fue encontrado");
        }
    }

    @Override
    public List<Producto> getAll() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    @Override
    public List<Producto> findByPrecio(double precio) {
        return productoRepository.findByPrecio(precio);
    }
    

    @Override
    public List<Producto> findByStock(int stock) {
        return productoRepository.findByStock(stock);
    }

    @Override
    public List<Producto> findByFilters(String nombre, double precio, int stock) {
        return productoRepository.findByFilters(nombre, precio, stock);
    }
}
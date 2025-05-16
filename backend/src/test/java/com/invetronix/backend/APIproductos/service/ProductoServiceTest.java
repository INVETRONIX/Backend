package com.invetronix.backend.APIproductos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.invetronix.backend.APIproductos.exception.ProductoNoEncontrado;
import com.invetronix.backend.APIproductos.model.Producto;
import com.invetronix.backend.APIproductos.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .id(1L)
                .nombre("Test Product")
                .descripcion("Test Description")
                .precio(100.0)
                .stock(10)
                .build();
    }

    @Test
    void create_ShouldReturnCreatedProducto() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto result = productoService.create(producto);

        assertNotNull(result);
        assertEquals(producto.getId(), result.getId());
        assertEquals(producto.getNombre(), result.getNombre());
        assertEquals(producto.getDescripcion(), result.getDescripcion());
        assertEquals(producto.getPrecio(), result.getPrecio());
        assertEquals(producto.getStock(), result.getStock());
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void getAll_ShouldReturnListOfProductos() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));

        List<Producto> result = productoService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(producto.getId(), result.get(0).getId());
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenNoProductos() {
        when(productoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Producto> result = productoService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getProductoById_ShouldReturnProducto() {
        when(productoRepository.getProductoById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> result = productoService.getProductoById(1L);

        assertTrue(result.isPresent());
        assertEquals(producto.getId(), result.get().getId());
        assertEquals(producto.getNombre(), result.get().getNombre());
        assertEquals(producto.getDescripcion(), result.get().getDescripcion());
        assertEquals(producto.getPrecio(), result.get().getPrecio());
        assertEquals(producto.getStock(), result.get().getStock());
    }

    @Test
    void getProductoById_ShouldThrowException_WhenProductoNotFound() {
        when(productoRepository.getProductoById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontrado.class, () -> productoService.getProductoById(1L));
    }

    @Test
    void update_ShouldReturnUpdatedProducto() {
        Producto updatedProducto = Producto.builder()
                .id(1L)
                .nombre("Updated Product")
                .descripcion("Updated Description")
                .precio(200.0)
                .stock(20)
                .build();

        when(productoRepository.getProductoById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(updatedProducto);

        Optional<Producto> result = productoService.update(1L, updatedProducto);

        assertTrue(result.isPresent());
        assertEquals(updatedProducto.getId(), result.get().getId());
        assertEquals(updatedProducto.getNombre(), result.get().getNombre());
        assertEquals(updatedProducto.getDescripcion(), result.get().getDescripcion());
        assertEquals(updatedProducto.getPrecio(), result.get().getPrecio());
        assertEquals(updatedProducto.getStock(), result.get().getStock());
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void update_ShouldThrowException_WhenProductoNotFound() {
        when(productoRepository.getProductoById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontrado.class, () -> productoService.update(1L, producto));
    }

    @Test
    void delete_ShouldReturnDeletedProducto() {
        when(productoRepository.getProductoById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> result = productoService.delete(1L);

        assertTrue(result.isPresent());
        assertEquals(producto.getId(), result.get().getId());
        assertEquals(producto.getNombre(), result.get().getNombre());
        assertEquals(producto.getDescripcion(), result.get().getDescripcion());
        assertEquals(producto.getPrecio(), result.get().getPrecio());
        assertEquals(producto.getStock(), result.get().getStock());
        verify(productoRepository).delete(producto);
    }

    @Test
    void delete_ShouldThrowException_WhenProductoNotFound() {
        when(productoRepository.getProductoById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontrado.class, () -> productoService.delete(1L));
    }

    @Test
    void findByNombre_ShouldReturnListOfProductos() {
        when(productoRepository.findByNombre("Test")).thenReturn(Arrays.asList(producto));

        List<Producto> result = productoService.findByNombre("Test");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(producto.getNombre(), result.get(0).getNombre());
    }

    @Test
    void findByNombre_ShouldReturnEmptyList_WhenNoProductosFound() {
        when(productoRepository.findByNombre("NonExistent")).thenReturn(Collections.emptyList());

        List<Producto> result = productoService.findByNombre("NonExistent");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByPrecio_ShouldReturnListOfProductos() {
        when(productoRepository.findByPrecio(100.0)).thenReturn(Arrays.asList(producto));

        List<Producto> result = productoService.findByPrecio(100.0);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(producto.getPrecio(), result.get(0).getPrecio());
    }

    @Test
    void findByPrecio_ShouldReturnEmptyList_WhenNoProductosFound() {
        when(productoRepository.findByPrecio(999.99)).thenReturn(Collections.emptyList());

        List<Producto> result = productoService.findByPrecio(999.99);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByStock_ShouldReturnListOfProductos() {
        when(productoRepository.findByStock(10)).thenReturn(Arrays.asList(producto));

        List<Producto> result = productoService.findByStock(10);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(producto.getStock(), result.get(0).getStock());
    }

    @Test
    void findByStock_ShouldReturnEmptyList_WhenNoProductosFound() {
        when(productoRepository.findByStock(999)).thenReturn(Collections.emptyList());

        List<Producto> result = productoService.findByStock(999);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilters_ShouldReturnListOfProductos() {
        when(productoRepository.findByFilters("Test", 100.0, 10)).thenReturn(Arrays.asList(producto));

        List<Producto> result = productoService.findByFilters("Test", 100.0, 10);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(producto.getNombre(), result.get(0).getNombre());
        assertEquals(producto.getPrecio(), result.get(0).getPrecio());
        assertEquals(producto.getStock(), result.get(0).getStock());
    }

    @Test
    void findByFilters_ShouldReturnEmptyList_WhenNoProductosFound() {
        when(productoRepository.findByFilters("NonExistent", 999.99, 999)).thenReturn(Collections.emptyList());

        List<Producto> result = productoService.findByFilters("NonExistent", 999.99, 999);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilters_ShouldWorkWithPartialFilters() {
        when(productoRepository.findByFilters("Test", null, null)).thenReturn(Arrays.asList(producto));

        List<Producto> result = productoService.findByFilters("Test", null, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(producto.getNombre(), result.get(0).getNombre());
    }
} 
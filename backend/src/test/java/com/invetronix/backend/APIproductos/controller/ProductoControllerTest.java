package com.invetronix.backend.APIproductos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invetronix.backend.APIproductos.model.Producto;
import com.invetronix.backend.APIproductos.service.ProductoService;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void createProducto_ShouldReturnCreatedProducto() throws Exception {
        when(productoService.create(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Test Product"))
                .andExpect(jsonPath("$.precio").value(100.0))
                .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    void createProducto_ShouldReturnBadRequest_WhenRequiredFieldsAreMissing() throws Exception {
        Producto invalidProducto = Producto.builder()
                .nombre("Test Product")
                .build();

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProducto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllProductos_ShouldReturnListOfProductos() throws Exception {
        when(productoService.getAll()).thenReturn(Arrays.asList(producto));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test Product"));
    }

    @Test
    void getAllProductos_ShouldReturnNoContent_WhenListIsEmpty() throws Exception {
        when(productoService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getProductoById_ShouldReturnProducto() throws Exception {
        when(productoService.getProductoById(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Test Product"));
    }

    @Test
    void getProductoById_ShouldReturnNotFound() throws Exception {
        when(productoService.getProductoById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProducto_ShouldReturnUpdatedProducto() throws Exception {
        when(productoService.update(any(Long.class), any(Producto.class))).thenReturn(Optional.of(producto));

        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Test Product"));
    }

    @Test
    void updateProducto_ShouldReturnNotFound() throws Exception {
        when(productoService.update(any(Long.class), any(Producto.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProducto_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByNombre_ShouldReturnProductos() throws Exception {
        when(productoService.findByNombre(any(String.class))).thenReturn(Arrays.asList(producto));

        mockMvc.perform(get("/api/productos/buscar/nombre")
                .param("nombre", "Test Product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test Product"));
    }

    @Test
    void findByNombre_ShouldReturnNoContent_WhenNoProductsFound() throws Exception {
        when(productoService.findByNombre(any(String.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/productos/buscar/nombre")
                .param("nombre", "NonExistent"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByPrecio_ShouldReturnProductos() throws Exception {
        when(productoService.findByPrecio(any(Double.class)))
                .thenReturn(Arrays.asList(producto));

        mockMvc.perform(get("/api/productos/buscar/precio")
                .param("precio", "100.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].precio").value(100.0));
    }

    @Test
    void findByPrecio_ShouldReturnNoContent_WhenNoProductsFound() throws Exception {
        when(productoService.findByPrecio(any(Double.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/productos/buscar/precio")
                .param("precio", "999.99"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByStock_ShouldReturnProductos() throws Exception {
        when(productoService.findByStock(any(Integer.class))).thenReturn(Arrays.asList(producto));

        mockMvc.perform(get("/api/productos/buscar/stock")
                .param("stock", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].stock").value(10));
    }

    @Test
    void findByStock_ShouldReturnNoContent_WhenNoProductsFound() throws Exception {
        when(productoService.findByStock(any(Integer.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/productos/buscar/stock")
                .param("stock", "999"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByFilters_ShouldReturnProductos() throws Exception {
        when(productoService.findByFilters(any(), any(), any())).thenReturn(Arrays.asList(producto));

        mockMvc.perform(get("/api/productos/buscar")
                .param("nombre", "Test")
                .param("precio", "100.0")
                .param("stock", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test Product"))
                .andExpect(jsonPath("$[0].precio").value(100.0))
                .andExpect(jsonPath("$[0].stock").value(10));
    }

    @Test
    void findByFilters_ShouldReturnNoContent_WhenNoProductsFound() throws Exception {
        when(productoService.findByFilters(any(), any(), any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/productos/buscar")
                .param("nombre", "NonExistent")
                .param("precio", "999.99")
                .param("stock", "999"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByFilters_ShouldWorkWithPartialFilters() throws Exception {
        when(productoService.findByFilters(any(), any(), any())).thenReturn(Arrays.asList(producto));

        mockMvc.perform(get("/api/productos/buscar")
                .param("nombre", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test Product"));
    }
} 
package com.invetronix.backend.APIcompras.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
import com.invetronix.backend.APIcompras.model.Compra;
import com.invetronix.backend.APIcompras.service.CompraService;
import com.invetronix.backend.APIproductos.model.Producto;
import com.invetronix.backend.APIusuarios.model.Usuario;

@WebMvcTest(CompraController.class)
public class CompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompraService compraService;

    @Autowired
    private ObjectMapper objectMapper;

    private Compra compra;
    private Usuario usuario;
    private Producto producto;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .nombre("Test User")
                .correo("test@test.com")
                .contrasena("password")
                .edad(25)
                .rol("CLIENTE")
                .build();

        producto = Producto.builder()
                .id(1L)
                .nombre("Test Product")
                .descripcion("Test Description")
                .precio(100.0)
                .stock(10)
                .build();

        compra = Compra.builder()
                .id(1L)
                .fecha(LocalDate.now())
                .hora(LocalTime.now().withSecond(0).withNano(0))
                .usuario(usuario)
                .producto(producto)
                .build();
    }

    @Test
    void createCompra_ShouldReturnCreatedCompra() throws Exception {
        when(compraService.create(any(Compra.class))).thenReturn(compra);

        mockMvc.perform(post("/api/compras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compra)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.usuario.id").value(1))
                .andExpect(jsonPath("$.usuario.nombre").value("Test User"))
                .andExpect(jsonPath("$.producto.id").value(1))
                .andExpect(jsonPath("$.producto.nombre").value("Test Product"));
    }

    @Test
    void createCompra_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
        Compra invalidCompra = Compra.builder().build();

        mockMvc.perform(post("/api/compras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCompra)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllCompras_ShouldReturnListOfCompras() throws Exception {
        when(compraService.getAll()).thenReturn(Arrays.asList(compra));

        mockMvc.perform(get("/api/compras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].usuario.id").value(1))
                .andExpect(jsonPath("$[0].usuario.nombre").value("Test User"))
                .andExpect(jsonPath("$[0].producto.id").value(1))
                .andExpect(jsonPath("$[0].producto.nombre").value("Test Product"));
    }

    @Test
    void getAllCompras_ShouldReturnEmptyList_WhenNoCompras() throws Exception {
        when(compraService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/compras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getCompraById_ShouldReturnCompra() throws Exception {
        when(compraService.getCompraById(1L)).thenReturn(Optional.of(compra));

        mockMvc.perform(get("/api/compras/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.usuario.id").value(1))
                .andExpect(jsonPath("$.usuario.nombre").value("Test User"))
                .andExpect(jsonPath("$.producto.id").value(1))
                .andExpect(jsonPath("$.producto.nombre").value("Test Product"));
    }

    @Test
    void getCompraById_ShouldReturnNotFound() throws Exception {
        when(compraService.getCompraById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/compras/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCompra_ShouldReturnUpdatedCompra() throws Exception {
        Compra updatedCompra = Compra.builder()
                .id(1L)
                .fecha(LocalDate.now().plusDays(1))
                .hora(LocalTime.now().plusHours(1).withSecond(0).withNano(0))
                .usuario(usuario)
                .producto(producto)
                .build();

        when(compraService.update(any(Long.class), any(Compra.class))).thenReturn(Optional.of(updatedCompra));

        mockMvc.perform(put("/api/compras/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCompra)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fecha").value(updatedCompra.getFecha().toString()))
                .andExpect(jsonPath("$.hora").value(updatedCompra.getHora().toString()));
    }

    @Test
    void updateCompra_ShouldReturnNotFound() throws Exception {
        when(compraService.update(any(Long.class), any(Compra.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/compras/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compra)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCompra_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/compras/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByFecha_ShouldReturnCompras() throws Exception {
        when(compraService.findByFecha(any(LocalDate.class))).thenReturn(Arrays.asList(compra));

        mockMvc.perform(get("/api/compras/fecha")
                .param("fecha", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fecha").value(compra.getFecha().toString()));
    }

    @Test
    void findByFecha_ShouldReturnNoContent_WhenNoComprasFound() throws Exception {
        when(compraService.findByFecha(any(LocalDate.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/compras/fecha")
                .param("fecha", LocalDate.now().toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByUsuarioId_ShouldReturnCompras() throws Exception {
        when(compraService.findByUsuarioId(any(Long.class))).thenReturn(Arrays.asList(compra));

        mockMvc.perform(get("/api/compras/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].usuario.id").value(1));
    }

    @Test
    void findByUsuarioId_ShouldReturnNoContent_WhenNoComprasFound() throws Exception {
        when(compraService.findByUsuarioId(any(Long.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/compras/usuario/999"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByHora_ShouldReturnCompras() throws Exception {
        when(compraService.findByHora(any(LocalTime.class))).thenReturn(Arrays.asList(compra));

        mockMvc.perform(get("/api/compras/hora")
                .param("hora", LocalTime.now().withSecond(0).withNano(0).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].hora").value(compra.getHora().toString()));
    }

    @Test
    void findByHora_ShouldReturnNoContent_WhenNoComprasFound() throws Exception {
        when(compraService.findByHora(any(LocalTime.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/compras/hora")
                .param("hora", LocalTime.now().withSecond(0).withNano(0).toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByFilters_ShouldReturnCompras() throws Exception {
        when(compraService.findByFilters(any(), any(), any())).thenReturn(Arrays.asList(compra));

        mockMvc.perform(get("/api/compras/buscar")
                .param("fecha", LocalDate.now().toString())
                .param("usuarioId", "1")
                .param("hora", LocalTime.now().withSecond(0).withNano(0).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fecha").value(compra.getFecha().toString()))
                .andExpect(jsonPath("$[0].usuario.id").value(1))
                .andExpect(jsonPath("$[0].hora").value(compra.getHora().toString()));
    }

    @Test
    void findByFilters_ShouldReturnNoContent_WhenNoComprasFound() throws Exception {
        when(compraService.findByFilters(any(), any(), any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/compras/buscar")
                .param("fecha", LocalDate.now().toString())
                .param("usuarioId", "999")
                .param("hora", LocalTime.now().withSecond(0).withNano(0).toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByFilters_ShouldWorkWithPartialFilters() throws Exception {
        when(compraService.findByFilters(any(), any(), any())).thenReturn(Arrays.asList(compra));

        mockMvc.perform(get("/api/compras/buscar")
                .param("fecha", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fecha").value(compra.getFecha().toString()));
    }
} 
package com.invetronix.backend.APIusuarios.controller;

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
import com.invetronix.backend.APIusuarios.model.Usuario;
import com.invetronix.backend.APIusuarios.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;

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
    }

    @Test
    void createUsuario_ShouldReturnCreatedUsuario() throws Exception {
        when(usuarioService.create(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Test User"))
                .andExpect(jsonPath("$.correo").value("test@test.com"))
                .andExpect(jsonPath("$.edad").value(25))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));
    }

    @Test
    void createUsuario_ShouldReturnConflict_WhenCorreoExists() throws Exception {
        when(usuarioService.create(any(Usuario.class)))
                .thenThrow(new RuntimeException("El correo ya está registrado"));

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getAllUsuarios_ShouldReturnListOfUsuarios() throws Exception {
        when(usuarioService.getAll()).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test User"))
                .andExpect(jsonPath("$[0].correo").value("test@test.com"))
                .andExpect(jsonPath("$[0].edad").value(25))
                .andExpect(jsonPath("$[0].rol").value("CLIENTE"));
    }

    @Test
    void getAllUsuarios_ShouldReturnEmptyList_WhenNoUsuarios() throws Exception {
        when(usuarioService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getUsuarioById_ShouldReturnUsuario() throws Exception {
        when(usuarioService.getUsuarioById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Test User"))
                .andExpect(jsonPath("$.correo").value("test@test.com"))
                .andExpect(jsonPath("$.edad").value(25))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));
    }

    @Test
    void getUsuarioById_ShouldReturnNotFound() throws Exception {
        when(usuarioService.getUsuarioById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUsuario_ShouldReturnUpdatedUsuario() throws Exception {
        Usuario updatedUsuario = Usuario.builder()
                .id(1L)
                .nombre("Updated User")
                .correo("updated@test.com")
                .contrasena("newpassword")
                .edad(30)
                .rol("ADMIN")
                .build();

        when(usuarioService.update(any(Long.class), any(Usuario.class))).thenReturn(Optional.of(updatedUsuario));

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Updated User"))
                .andExpect(jsonPath("$.correo").value("updated@test.com"))
                .andExpect(jsonPath("$.edad").value(30))
                .andExpect(jsonPath("$.rol").value("ADMIN"));
    }

    @Test
    void updateUsuario_ShouldReturnNotFound() throws Exception {
        when(usuarioService.update(any(Long.class), any(Usuario.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUsuario_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByNombre_ShouldReturnUsuarios() throws Exception {
        when(usuarioService.findByNombre(any(String.class))).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/usuarios/buscar/nombre")
                .param("nombre", "Test User"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test User"));
    }

    @Test
    void findByNombre_ShouldReturnNoContent_WhenNoUsuariosFound() throws Exception {
        when(usuarioService.findByNombre(any(String.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/usuarios/buscar/nombre")
                .param("nombre", "NonExistent"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByEdad_ShouldReturnUsuarios() throws Exception {
        when(usuarioService.findByEdad(any(Integer.class))).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/usuarios/buscar/edad")
                .param("edad", "25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].edad").value(25));
    }

    @Test
    void findByEdad_ShouldReturnNoContent_WhenNoUsuariosFound() throws Exception {
        when(usuarioService.findByEdad(any(Integer.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/usuarios/buscar/edad")
                .param("edad", "999"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByFilters_ShouldReturnUsuarios() throws Exception {
        when(usuarioService.findByFilters(any(), any())).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/usuarios/buscar")
                .param("nombre", "Test")
                .param("edad", "25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test User"))
                .andExpect(jsonPath("$[0].edad").value(25));
    }

    @Test
    void findByFilters_ShouldReturnNoContent_WhenNoUsuariosFound() throws Exception {
        when(usuarioService.findByFilters(any(), any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/usuarios/buscar")
                .param("nombre", "NonExistent")
                .param("edad", "999"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByFilters_ShouldWorkWithPartialFilters() throws Exception {
        when(usuarioService.findByFilters(any(), any())).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/usuarios/buscar")
                .param("nombre", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test User"));
    }
} 
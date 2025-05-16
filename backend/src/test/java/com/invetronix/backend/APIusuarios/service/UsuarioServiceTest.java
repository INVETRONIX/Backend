package com.invetronix.backend.APIusuarios.service;

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
import com.invetronix.backend.APIusuarios.exception.UsuarioNoEncontrado;
import com.invetronix.backend.APIusuarios.exception.UsuarioYaRegistrado;
import com.invetronix.backend.APIusuarios.model.Usuario;
import com.invetronix.backend.APIusuarios.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

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
    void create_ShouldReturnCreatedUsuario() {
        when(usuarioRepository.getByCorreo(any(String.class))).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.create(usuario);

        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        assertEquals(usuario.getNombre(), result.getNombre());
        assertEquals(usuario.getCorreo(), result.getCorreo());
        assertEquals(usuario.getEdad(), result.getEdad());
        assertEquals("CLIENTE", result.getRol());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void create_ShouldThrowException_WhenUsuarioAlreadyExists() {
        when(usuarioRepository.getByCorreo(any(String.class))).thenReturn(Optional.of(usuario));

        assertThrows(UsuarioYaRegistrado.class, () -> usuarioService.create(usuario));
    }

    @Test
    void getAll_ShouldReturnListOfUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));

        List<Usuario> result = usuarioService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(usuario.getId(), result.get(0).getId());
        assertEquals(usuario.getNombre(), result.get(0).getNombre());
        assertEquals(usuario.getCorreo(), result.get(0).getCorreo());
        assertEquals(usuario.getEdad(), result.get(0).getEdad());
        assertEquals(usuario.getRol(), result.get(0).getRol());
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenNoUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<Usuario> result = usuarioService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getUsuarioById_ShouldReturnUsuario() {
        when(usuarioRepository.getUsuarioById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.getUsuarioById(1L);

        assertTrue(result.isPresent());
        assertEquals(usuario.getId(), result.get().getId());
        assertEquals(usuario.getNombre(), result.get().getNombre());
        assertEquals(usuario.getCorreo(), result.get().getCorreo());
        assertEquals(usuario.getEdad(), result.get().getEdad());
        assertEquals(usuario.getRol(), result.get().getRol());
    }

    @Test
    void getUsuarioById_ShouldThrowException_WhenUsuarioNotFound() {
        when(usuarioRepository.getUsuarioById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontrado.class, () -> usuarioService.getUsuarioById(1L));
    }

    @Test
    void update_ShouldReturnUpdatedUsuario() {
        Usuario updatedUsuario = Usuario.builder()
                .id(1L)
                .nombre("Updated User")
                .correo("updated@test.com")
                .contrasena("newpassword")
                .edad(30)
                .rol("ADMIN")
                .build();

        when(usuarioRepository.getUsuarioById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(updatedUsuario);

        Optional<Usuario> result = usuarioService.update(1L, updatedUsuario);

        assertTrue(result.isPresent());
        assertEquals(updatedUsuario.getId(), result.get().getId());
        assertEquals(updatedUsuario.getNombre(), result.get().getNombre());
        assertEquals(updatedUsuario.getCorreo(), result.get().getCorreo());
        assertEquals(updatedUsuario.getEdad(), result.get().getEdad());
        assertEquals(updatedUsuario.getRol(), result.get().getRol());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void update_ShouldThrowException_WhenUsuarioNotFound() {
        when(usuarioRepository.getUsuarioById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontrado.class, () -> usuarioService.update(1L, usuario));
    }

    @Test
    void delete_ShouldReturnDeletedUsuario() {
        when(usuarioRepository.getUsuarioById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.delete(1L);

        assertTrue(result.isPresent());
        assertEquals(usuario.getId(), result.get().getId());
        assertEquals(usuario.getNombre(), result.get().getNombre());
        assertEquals(usuario.getCorreo(), result.get().getCorreo());
        assertEquals(usuario.getEdad(), result.get().getEdad());
        assertEquals(usuario.getRol(), result.get().getRol());
        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void delete_ShouldThrowException_WhenUsuarioNotFound() {
        when(usuarioRepository.getUsuarioById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontrado.class, () -> usuarioService.delete(1L));
    }

    @Test
    void findByNombre_ShouldReturnListOfUsuarios() {
        when(usuarioRepository.findByNombre("Test")).thenReturn(Arrays.asList(usuario));

        List<Usuario> result = usuarioService.findByNombre("Test");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(usuario.getNombre(), result.get(0).getNombre());
    }

    @Test
    void findByNombre_ShouldReturnEmptyList_WhenNoUsuariosFound() {
        when(usuarioRepository.findByNombre("NonExistent")).thenReturn(Collections.emptyList());

        List<Usuario> result = usuarioService.findByNombre("NonExistent");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByEdad_ShouldReturnListOfUsuarios() {
        when(usuarioRepository.findByEdad(25)).thenReturn(Arrays.asList(usuario));

        List<Usuario> result = usuarioService.findByEdad(25);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(usuario.getEdad(), result.get(0).getEdad());
    }

    @Test
    void findByEdad_ShouldReturnEmptyList_WhenNoUsuariosFound() {
        when(usuarioRepository.findByEdad(999)).thenReturn(Collections.emptyList());

        List<Usuario> result = usuarioService.findByEdad(999);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilters_ShouldReturnListOfUsuarios() {
        when(usuarioRepository.findByFilters("Test", 25)).thenReturn(Arrays.asList(usuario));

        List<Usuario> result = usuarioService.findByFilters("Test", 25);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(usuario.getNombre(), result.get(0).getNombre());
        assertEquals(usuario.getEdad(), result.get(0).getEdad());
    }

    @Test
    void findByFilters_ShouldReturnEmptyList_WhenNoUsuariosFound() {
        when(usuarioRepository.findByFilters("NonExistent", 999)).thenReturn(Collections.emptyList());

        List<Usuario> result = usuarioService.findByFilters("NonExistent", 999);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilters_ShouldWorkWithPartialFilters() {
        when(usuarioRepository.findByFilters("Test", null)).thenReturn(Arrays.asList(usuario));

        List<Usuario> result = usuarioService.findByFilters("Test", null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(usuario.getNombre(), result.get(0).getNombre());
    }
} 
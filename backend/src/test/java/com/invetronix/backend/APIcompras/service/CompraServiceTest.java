package com.invetronix.backend.APIcompras.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
import com.invetronix.backend.APIcompras.exception.CompraNoEncontrada;
import com.invetronix.backend.APIcompras.model.Compra;
import com.invetronix.backend.APIcompras.repository.CompraRepository;
import com.invetronix.backend.APIproductos.model.Producto;
import com.invetronix.backend.APIusuarios.model.Usuario;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @InjectMocks
    private CompraService compraService;

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
    void create_ShouldReturnCreatedCompra() {
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);

        Compra result = compraService.create(compra);

        assertNotNull(result);
        assertEquals(compra.getId(), result.getId());
        assertEquals(compra.getFecha(), result.getFecha());
        assertEquals(compra.getHora(), result.getHora());
        assertEquals(compra.getUsuario().getId(), result.getUsuario().getId());
        assertEquals(compra.getUsuario().getNombre(), result.getUsuario().getNombre());
        assertEquals(compra.getProducto().getId(), result.getProducto().getId());
        assertEquals(compra.getProducto().getNombre(), result.getProducto().getNombre());
        verify(compraRepository).save(any(Compra.class));
    }

    @Test
    void getAll_ShouldReturnListOfCompras() {
        when(compraRepository.findAll()).thenReturn(Arrays.asList(compra));

        List<Compra> result = compraService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(compra.getId(), result.get(0).getId());
        assertEquals(compra.getFecha(), result.get(0).getFecha());
        assertEquals(compra.getHora(), result.get(0).getHora());
        assertEquals(compra.getUsuario().getId(), result.get(0).getUsuario().getId());
        assertEquals(compra.getProducto().getId(), result.get(0).getProducto().getId());
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenNoCompras() {
        when(compraRepository.findAll()).thenReturn(Collections.emptyList());

        List<Compra> result = compraService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getCompraById_ShouldReturnCompra() {
        when(compraRepository.getCompraById(1L)).thenReturn(Optional.of(compra));

        Optional<Compra> result = compraService.getCompraById(1L);

        assertTrue(result.isPresent());
        assertEquals(compra.getId(), result.get().getId());
        assertEquals(compra.getFecha(), result.get().getFecha());
        assertEquals(compra.getHora(), result.get().getHora());
        assertEquals(compra.getUsuario().getId(), result.get().getUsuario().getId());
        assertEquals(compra.getProducto().getId(), result.get().getProducto().getId());
    }

    @Test
    void getCompraById_ShouldThrowException_WhenCompraNotFound() {
        when(compraRepository.getCompraById(1L)).thenReturn(Optional.empty());

        assertThrows(CompraNoEncontrada.class, () -> compraService.getCompraById(1L));
    }

    @Test
    void update_ShouldReturnUpdatedCompra() {
        Compra updatedCompra = Compra.builder()
                .id(1L)
                .fecha(LocalDate.now().plusDays(1))
                .hora(LocalTime.now().plusHours(1).withSecond(0).withNano(0))
                .usuario(usuario)
                .producto(producto)
                .build();

        when(compraRepository.getCompraById(1L)).thenReturn(Optional.of(compra));
        when(compraRepository.save(any(Compra.class))).thenReturn(updatedCompra);

        Optional<Compra> result = compraService.update(1L, updatedCompra);

        assertTrue(result.isPresent());
        assertEquals(updatedCompra.getId(), result.get().getId());
        assertEquals(updatedCompra.getFecha(), result.get().getFecha());
        assertEquals(updatedCompra.getHora(), result.get().getHora());
        assertEquals(updatedCompra.getUsuario().getId(), result.get().getUsuario().getId());
        assertEquals(updatedCompra.getProducto().getId(), result.get().getProducto().getId());
        verify(compraRepository).save(any(Compra.class));
    }

    @Test
    void update_ShouldThrowException_WhenCompraNotFound() {
        when(compraRepository.getCompraById(1L)).thenReturn(Optional.empty());

        assertThrows(CompraNoEncontrada.class, () -> compraService.update(1L, compra));
    }

    @Test
    void delete_ShouldReturnDeletedCompra() {
        when(compraRepository.getCompraById(1L)).thenReturn(Optional.of(compra));

        Optional<Compra> result = compraService.delete(1L);

        assertTrue(result.isPresent());
        assertEquals(compra.getId(), result.get().getId());
        assertEquals(compra.getFecha(), result.get().getFecha());
        assertEquals(compra.getHora(), result.get().getHora());
        assertEquals(compra.getUsuario().getId(), result.get().getUsuario().getId());
        assertEquals(compra.getProducto().getId(), result.get().getProducto().getId());
        verify(compraRepository).delete(compra);
    }

    @Test
    void delete_ShouldThrowException_WhenCompraNotFound() {
        when(compraRepository.getCompraById(1L)).thenReturn(Optional.empty());

        assertThrows(CompraNoEncontrada.class, () -> compraService.delete(1L));
    }

    @Test
    void findByFecha_ShouldReturnListOfCompras() {
        LocalDate fecha = LocalDate.now();
        when(compraRepository.findByFecha(fecha)).thenReturn(Arrays.asList(compra));

        List<Compra> result = compraService.findByFecha(fecha);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(compra.getFecha(), result.get(0).getFecha());
        assertEquals(compra.getId(), result.get(0).getId());
        assertEquals(compra.getUsuario().getId(), result.get(0).getUsuario().getId());
        assertEquals(compra.getProducto().getId(), result.get(0).getProducto().getId());
    }

    @Test
    void findByFecha_ShouldReturnEmptyList_WhenNoComprasFound() {
        LocalDate fecha = LocalDate.now();
        when(compraRepository.findByFecha(fecha)).thenReturn(Collections.emptyList());

        List<Compra> result = compraService.findByFecha(fecha);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByUsuarioId_ShouldReturnListOfCompras() {
        when(compraRepository.findByUsuarioId(1L)).thenReturn(Arrays.asList(compra));

        List<Compra> result = compraService.findByUsuarioId(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(compra.getUsuario().getId(), result.get(0).getUsuario().getId());
        assertEquals(compra.getId(), result.get(0).getId());
        assertEquals(compra.getFecha(), result.get(0).getFecha());
        assertEquals(compra.getHora(), result.get(0).getHora());
    }

    @Test
    void findByUsuarioId_ShouldReturnEmptyList_WhenNoComprasFound() {
        when(compraRepository.findByUsuarioId(999L)).thenReturn(Collections.emptyList());

        List<Compra> result = compraService.findByUsuarioId(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByHora_ShouldReturnListOfCompras() {
        LocalTime hora = LocalTime.now().withSecond(0).withNano(0);
        when(compraRepository.findByHora(hora)).thenReturn(Arrays.asList(compra));

        List<Compra> result = compraService.findByHora(hora);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(compra.getHora(), result.get(0).getHora());
        assertEquals(compra.getId(), result.get(0).getId());
        assertEquals(compra.getFecha(), result.get(0).getFecha());
        assertEquals(compra.getUsuario().getId(), result.get(0).getUsuario().getId());
    }

    @Test
    void findByHora_ShouldReturnEmptyList_WhenNoComprasFound() {
        LocalTime hora = LocalTime.now().withSecond(0).withNano(0);
        when(compraRepository.findByHora(hora)).thenReturn(Collections.emptyList());

        List<Compra> result = compraService.findByHora(hora);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilters_ShouldReturnListOfCompras() {
        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now().withSecond(0).withNano(0);
        when(compraRepository.findByFilters(fecha, 1L, hora)).thenReturn(Arrays.asList(compra));

        List<Compra> result = compraService.findByFilters(fecha, 1L, hora);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(compra.getFecha(), result.get(0).getFecha());
        assertEquals(compra.getUsuario().getId(), result.get(0).getUsuario().getId());
        assertEquals(compra.getHora(), result.get(0).getHora());
        assertEquals(compra.getId(), result.get(0).getId());
    }

    @Test
    void findByFilters_ShouldReturnEmptyList_WhenNoComprasFound() {
        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now().withSecond(0).withNano(0);
        when(compraRepository.findByFilters(fecha, 999L, hora)).thenReturn(Collections.emptyList());

        List<Compra> result = compraService.findByFilters(fecha, 999L, hora);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilters_ShouldWorkWithPartialFilters() {
        LocalDate fecha = LocalDate.now();
        when(compraRepository.findByFilters(fecha, null, null)).thenReturn(Arrays.asList(compra));

        List<Compra> result = compraService.findByFilters(fecha, null, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(compra.getFecha(), result.get(0).getFecha());
        assertEquals(compra.getId(), result.get(0).getId());
    }
} 
package com.invetronix.backend.APIproductos.controller;

import org.springframework.web.bind.annotation.RestController;
import com.invetronix.backend.APIproductos.model.Producto;
import com.invetronix.backend.APIproductos.service.IProductoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para la gestión de productos")
public class ProductoController {

    @Autowired
    private final IProductoService productoService;

    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto en el sistema. Todos los campos son obligatorios.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos - todos los campos son obligatorios"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Producto> create(@RequestBody Producto producto) {
        Producto saved = productoService.create(producto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "No hay productos registrados")
    })
    @GetMapping
    public ResponseEntity<List<Producto>> getAll() {
        List<Producto> productos = productoService.getAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    
    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(
        @Parameter(description = "ID del producto a buscar") @PathVariable Long id) {
        Optional<Producto> producto = productoService.getProductoById(id);
        return producto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @Operation(summary = "Actualizar producto", description = "Actualiza la información de un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(
        @Parameter(description = "ID del producto a actualizar") @PathVariable Long id,
        @RequestBody Producto producto) {
        Optional<Producto> updated = productoService.update(id, producto);
        return updated.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID del producto a eliminar") @PathVariable Long id) {
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @Operation(summary = "Buscar productos por nombre", description = "Retorna productos que coincidan con el nombre proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron productos")
    })
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Producto>> findByNombre(
        @Parameter(description = "Nombre a buscar") @RequestParam String nombre) {
        List<Producto> productos = productoService.findByNombre(nombre);
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    
    @Operation(summary = "Buscar productos por precio", description = "Retorna productos que coincidan con el precio proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron productos")
    })
    @GetMapping("/buscar/precio")
    public ResponseEntity<List<Producto>> findByPrecio(
        @Parameter(description = "Precio a buscar") @RequestParam double precio) {
        List<Producto> productos = productoService.findByPrecio(precio);
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    
    @Operation(summary = "Buscar productos por stock", description = "Retorna productos que coincidan con el stock proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron productos")
    })
    @GetMapping("/buscar/stock")
    public ResponseEntity<List<Producto>> findByStock(
        @Parameter(description = "Stock a buscar") @RequestParam int stock) {
        List<Producto> productos = productoService.findByStock(stock);
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    
    @Operation(summary = "Buscar productos por filtros", description = "Retorna productos que coincidan con los filtros proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados exitosamente",
            content = @Content(schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron productos")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> findByFilters(
        @Parameter(description = "Nombre a buscar (opcional)") @RequestParam(required = false) String nombre,
        @Parameter(description = "Precio a buscar (opcional)") @RequestParam(required = false) Double precio,
        @Parameter(description = "Stock a buscar (opcional)") @RequestParam(required = false) Integer stock) {
        List<Producto> productos = productoService.findByFilters(nombre, precio, stock);
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}

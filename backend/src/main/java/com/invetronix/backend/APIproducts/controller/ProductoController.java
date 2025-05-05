package com.invetronix.backend.APIproducts.controller;

import com.invetronix.backend.APIproducts.model.Producto;
import com.invetronix.backend.APIproducts.service.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "API para gestionar productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @Operation(summary = "Obtener todos los productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos encontrada")
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(
            @Parameter(description = "ID del producto a buscar", example = "1")
            @PathVariable Long id) {
        Producto producto = productoService.getProductoById(id);
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Crear nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    @PostMapping
    public ResponseEntity<Producto> createProducto(
            @Parameter(description = "Datos del producto a crear")
            @RequestBody Producto producto) {
        Producto savedProducto = productoService.saveProducto(producto);
        return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
            @Parameter(description = "ID del producto a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del producto")
            @RequestBody Producto productoDetails) {
        Producto updatedProducto = productoService.updateProducto(id, productoDetails);
        return ResponseEntity.ok(updatedProducto);
    }

    @Operation(summary = "Eliminar producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(
            @Parameter(description = "ID del producto a eliminar", example = "1")
            @PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar productos por filtros")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos encontrada"),
        @ApiResponse(responseCode = "204", description = "No se encontraron productos")
    })
    @GetMapping("/filtros")
    public ResponseEntity<List<Producto>> findByFilters(
            @Parameter(description = "Nombre a buscar", example = "Laptop")
            @RequestParam(required = false) String nombre,
            @Parameter(description = "Categoría a buscar", example = "Electrónicos")
            @RequestParam(required = false) String categoria,
            @Parameter(description = "Proveedor a buscar", example = "HP")
            @RequestParam(required = false) String proveedor) {
        List<Producto> productos = productoService.findByFilters(nombre, categoria, proveedor);
        return productos.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(productos);
    }

    @Operation(summary = "Buscar productos por nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos encontrada"),
        @ApiResponse(responseCode = "204", description = "No se encontraron productos")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> findByNombre(
            @Parameter(description = "Nombre a buscar", example = "Laptop")
            @PathVariable String nombre) {
        List<Producto> productos = productoService.findByNombre(nombre);
        return productos.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(productos);
    }

    @Operation(summary = "Buscar productos por categoría")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos encontrada"),
        @ApiResponse(responseCode = "204", description = "No se encontraron productos")
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> findByCategoria(
            @Parameter(description = "Categoría a buscar", example = "Electrónicos")
            @PathVariable String categoria) {
        List<Producto> productos = productoService.findByCategoria(categoria);
        return productos.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(productos);
    }

    @Operation(summary = "Buscar productos por proveedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos encontrada"),
        @ApiResponse(responseCode = "204", description = "No se encontraron productos")
    })
    @GetMapping("/proveedor/{proveedor}")
    public ResponseEntity<List<Producto>> findByProveedor(
            @Parameter(description = "Proveedor a buscar", example = "HP")
            @PathVariable String proveedor) {
        List<Producto> productos = productoService.findByProveedor(proveedor);
        return productos.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(productos);
    }
} 
package com.invetronix.backend.APIcompras.controller;

import com.invetronix.backend.APIcompras.exception.CompraNotFoundException;
import com.invetronix.backend.APIcompras.model.Compra;
import com.invetronix.backend.APIcompras.service.ICompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
@Tag(name = "Compras", description = "API para gestionar compras")
public class CompraController {

    @Autowired
    private ICompraService compraService;

    @GetMapping
    @Operation(summary = "Obtener todas las compras")
    public ResponseEntity<List<Compra>> getAllCompras() {
        return ResponseEntity.ok(compraService.getAllCompras());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una compra por ID")
    public ResponseEntity<Compra> getCompraById(
            @Parameter(description = "ID de la compra") @PathVariable Long id) throws CompraNotFoundException {
        return ResponseEntity.ok(compraService.getCompraById(id));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva compra")
    public ResponseEntity<Compra> createCompra(
            @Parameter(description = "Datos de la compra") @RequestBody Compra compra) {
        Compra savedCompra = compraService.saveCompra(compra);
        return new ResponseEntity<>(savedCompra, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una compra existente")
    public ResponseEntity<Compra> updateCompra(
            @Parameter(description = "ID de la compra") @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la compra") @RequestBody Compra compra) throws CompraNotFoundException {
        Compra updatedCompra = compraService.updateCompra(id, compra);
        return ResponseEntity.ok(updatedCompra);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una compra")
    public ResponseEntity<Void> deleteCompra(
            @Parameter(description = "ID de la compra") @PathVariable Long id) throws CompraNotFoundException {
        compraService.deleteCompra(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener compras por ID de usuario")
    public ResponseEntity<List<Compra>> getComprasByUsuarioId(
            @Parameter(description = "ID del usuario") @PathVariable Long usuarioId) {
        List<Compra> compras = compraService.findByUsuarioId(usuarioId);
        return compras.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(compras);
    }

    @GetMapping("/fecha/inicio/{fechaInicio}")
    @Operation(summary = "Obtener compras desde una fecha")
    public ResponseEntity<List<Compra>> getComprasByFechaInicio(
            @Parameter(description = "Fecha de inicio (formato: yyyy-MM-dd)") @PathVariable String fechaInicio) {
        List<Compra> compras = compraService.findByFechaInicio(fechaInicio);
        return compras.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(compras);
    }

    @GetMapping("/fecha/fin/{fechaFin}")
    @Operation(summary = "Obtener compras hasta una fecha")
    public ResponseEntity<List<Compra>> getComprasByFechaFin(
            @Parameter(description = "Fecha de fin (formato: yyyy-MM-dd)") @PathVariable String fechaFin) {
        List<Compra> compras = compraService.findByFechaFin(fechaFin);
        return compras.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(compras);
    }

    @GetMapping("/total/min/{totalMin}")
    @Operation(summary = "Obtener compras con total mínimo")
    public ResponseEntity<List<Compra>> getComprasByTotalMin(
            @Parameter(description = "Total mínimo") @PathVariable Double totalMin) {
        List<Compra> compras = compraService.findByTotalMin(totalMin);
        return compras.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(compras);
    }

    @GetMapping("/total/max/{totalMax}")
    @Operation(summary = "Obtener compras con total máximo")
    public ResponseEntity<List<Compra>> getComprasByTotalMax(
            @Parameter(description = "Total máximo") @PathVariable Double totalMax) {
        List<Compra> compras = compraService.findByTotalMax(totalMax);
        return compras.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(compras);
    }

    @GetMapping("/filtros")
    @Operation(summary = "Buscar compras por filtros combinados")
    public ResponseEntity<List<Compra>> getComprasByFilters(
            @Parameter(description = "ID del usuario") @RequestParam(required = false) Long usuarioId,
            @Parameter(description = "Fecha de inicio (formato: yyyy-MM-dd)") @RequestParam(required = false) String fechaInicio,
            @Parameter(description = "Fecha de fin (formato: yyyy-MM-dd)") @RequestParam(required = false) String fechaFin,
            @Parameter(description = "Total mínimo") @RequestParam(required = false) Double totalMin,
            @Parameter(description = "Total máximo") @RequestParam(required = false) Double totalMax) {
        List<Compra> compras = compraService.findByFilters(usuarioId, fechaInicio, fechaFin, totalMin, totalMax);
        return compras.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(compras);
    }
} 
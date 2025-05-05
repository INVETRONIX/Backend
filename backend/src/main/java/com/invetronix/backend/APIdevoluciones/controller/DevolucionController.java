package com.invetronix.backend.APIdevoluciones.controller;

import com.invetronix.backend.APIdevoluciones.model.Devolucion;
import com.invetronix.backend.APIdevoluciones.service.IDevolucionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/devoluciones")
@Tag(name = "Devoluciones", description = "API para gestionar devoluciones")
public class DevolucionController {

    @Autowired
    private IDevolucionService devolucionService;

    @GetMapping
    @Operation(summary = "Obtener todas las devoluciones")
    public ResponseEntity<List<Devolucion>> getAllDevoluciones() {
        List<Devolucion> devoluciones = devolucionService.getAllDevoluciones();
        return devoluciones.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(devoluciones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una devolución por ID")
    public ResponseEntity<Devolucion> getDevolucionById(
            @Parameter(description = "ID de la devolución") @PathVariable Long id) {
        Devolucion devolucion = devolucionService.getDevolucionById(id);
        return ResponseEntity.ok(devolucion);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva devolución")
    public ResponseEntity<Devolucion> createDevolucion(
            @Parameter(description = "Datos de la devolución") @RequestBody Devolucion devolucion) {
        Devolucion savedDevolucion = devolucionService.saveDevolucion(devolucion);
        return new ResponseEntity<>(savedDevolucion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una devolución existente")
    public ResponseEntity<Devolucion> updateDevolucion(
            @Parameter(description = "ID de la devolución") @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la devolución") @RequestBody Devolucion devolucion) {
        Devolucion updatedDevolucion = devolucionService.updateDevolucion(id, devolucion);
        return ResponseEntity.ok(updatedDevolucion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una devolución")
    public ResponseEntity<Void> deleteDevolucion(
            @Parameter(description = "ID de la devolución") @PathVariable Long id) {
        devolucionService.deleteDevolucion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/compra/{compraId}")
    @Operation(summary = "Obtener devolución por ID de compra")
    public ResponseEntity<Devolucion> getDevolucionByCompraId(
            @Parameter(description = "ID de la compra") @PathVariable Long compraId) {
        Devolucion devolucion = devolucionService.findByCompraId(compraId);
        return ResponseEntity.ok(devolucion);
    }

    @GetMapping("/fecha/inicio/{fechaInicio}")
    @Operation(summary = "Obtener devoluciones desde una fecha")
    public ResponseEntity<List<Devolucion>> getDevolucionesByFechaInicio(
            @Parameter(description = "Fecha de inicio (formato: yyyy-MM-dd)") @PathVariable String fechaInicio) {
        List<Devolucion> devoluciones = devolucionService.findByFechaInicio(fechaInicio);
        return devoluciones.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(devoluciones);
    }

    @GetMapping("/fecha/fin/{fechaFin}")
    @Operation(summary = "Obtener devoluciones hasta una fecha")
    public ResponseEntity<List<Devolucion>> getDevolucionesByFechaFin(
            @Parameter(description = "Fecha de fin (formato: yyyy-MM-dd)") @PathVariable String fechaFin) {
        List<Devolucion> devoluciones = devolucionService.findByFechaFin(fechaFin);
        return devoluciones.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(devoluciones);
    }

    @GetMapping("/filtros")
    @Operation(summary = "Buscar devoluciones por filtros combinados")
    public ResponseEntity<List<Devolucion>> getDevolucionesByFilters(
            @Parameter(description = "ID de la compra") @RequestParam(required = false) Long compraId,
            @Parameter(description = "Fecha de inicio (formato: yyyy-MM-dd)") @RequestParam(required = false) String fechaInicio,
            @Parameter(description = "Fecha de fin (formato: yyyy-MM-dd)") @RequestParam(required = false) String fechaFin) {
        List<Devolucion> devoluciones = devolucionService.findByFilters(compraId, fechaInicio, fechaFin);
        return devoluciones.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok(devoluciones);
    }
}
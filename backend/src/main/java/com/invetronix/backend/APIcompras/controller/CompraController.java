package com.invetronix.backend.APIcompras.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.invetronix.backend.APIcompras.model.Compra;
import com.invetronix.backend.APIcompras.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
@Tag(name = "Compras", description = "API para la gestión de compras")
public class CompraController {
    
    @Autowired
    private CompraService compraService;

    @Operation(summary = "Crear una nueva compra", description = "Crea una nueva compra en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Compra creada exitosamente",
            content = @Content(schema = @Schema(implementation = Compra.class))),
        @ApiResponse(responseCode = "400", description = "Datos de compra inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Compra> create(@RequestBody Compra compra){
        Compra saved = compraService.create(compra);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todas las compras", description = "Retorna una lista de todas las compras registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de compras encontrada",
            content = @Content(schema = @Schema(implementation = Compra.class))),
        @ApiResponse(responseCode = "204", description = "No hay compras registradas")
    })
    @GetMapping
    public ResponseEntity<List<Compra>> getAll(){
        List<Compra> compras = compraService.getAll();
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una compra por ID", description = "Retorna una compra específica basada en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compra encontrada",
            content = @Content(schema = @Schema(implementation = Compra.class))),
        @ApiResponse(responseCode = "404", description = "Compra no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Compra> getById(
        @Parameter(description = "ID de la compra a buscar", required = true)
        @PathVariable Long id){
        Optional<Compra> compra = compraService.getCompraById(id);
        return compra.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Actualizar una compra", description = "Actualiza los datos de una compra existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compra actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = Compra.class))),
        @ApiResponse(responseCode = "404", description = "Compra no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Compra> update(
        @Parameter(description = "ID de la compra a actualizar", required = true)
        @PathVariable Long id,
        @RequestBody Compra compra){
        Optional<Compra> updated = compraService.update(id, compra);
        return updated.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Eliminar una compra", description = "Elimina una compra del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Compra eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Compra no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID de la compra a eliminar", required = true)
        @PathVariable Long id){
        compraService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Buscar compras por fecha", description = "Retorna las compras realizadas en una fecha específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compras encontradas",
            content = @Content(schema = @Schema(implementation = Compra.class))),
        @ApiResponse(responseCode = "204", description = "No hay compras en la fecha especificada")
    })
    @GetMapping("/fecha")
    public ResponseEntity<List<Compra>> findByFecha(
        @Parameter(description = "Fecha de las compras a buscar", required = true)
        @RequestParam LocalDate fecha){
        List<Compra> compras = compraService.findByFecha(fecha);
        if(compras.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @Operation(summary = "Buscar compras por usuario", description = "Retorna las compras realizadas por un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compras encontradas",
            content = @Content(schema = @Schema(implementation = Compra.class))),
        @ApiResponse(responseCode = "204", description = "No hay compras para el usuario especificado")
    })
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Compra>> findByUsuarioId(
        @Parameter(description = "ID del usuario", required = true)
        @PathVariable Long id){
        List<Compra> compras = compraService.findByUsuarioId(id);
        if(compras.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @Operation(summary = "Buscar compras por hora", description = "Retorna las compras realizadas en una hora específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compras encontradas",
            content = @Content(schema = @Schema(implementation = Compra.class))),
        @ApiResponse(responseCode = "204", description = "No hay compras en la hora especificada")
    })
    @GetMapping("/hora")
    public ResponseEntity<List<Compra>> findByHora(
        @Parameter(description = "Hora de las compras a buscar", required = true)
        @RequestParam LocalTime hora){
        List<Compra> compras = compraService.findByHora(hora);
        if(compras.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @Operation(summary = "Buscar compras con filtros", description = "Retorna las compras que coinciden con los filtros especificados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compras encontradas",
            content = @Content(schema = @Schema(implementation = Compra.class))),
        @ApiResponse(responseCode = "204", description = "No hay compras que coincidan con los filtros")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<Compra>> findByFilters(
        @Parameter(description = "Fecha de las compras (opcional)")
        @RequestParam(required = false) LocalDate fecha,
        @Parameter(description = "ID del usuario (opcional)")
        @RequestParam(required = false) Long usuarioId,
        @Parameter(description = "Hora de las compras (opcional)")
        @RequestParam(required = false) LocalTime hora){
        List<Compra> compras = compraService.findByFilters(fecha, usuarioId, hora);
        if(compras.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }
}

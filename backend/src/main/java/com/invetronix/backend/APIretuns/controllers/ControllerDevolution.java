package com.invetronix.backend.APIretuns.controllers;

import java.time.LocalDate;
import java.time.LocalTime;

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
import com.invetronix.backend.APIproducts.dtos.DtoProduct;
import com.invetronix.backend.APIretuns.controllers.in.IDeleteOperation;
import com.invetronix.backend.APIretuns.controllers.in.IGetOperations;
import com.invetronix.backend.APIretuns.controllers.in.IPostOperation;
import com.invetronix.backend.APIretuns.dtos.DtoDevolution;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Registro de productos", description = "API para operaciones CRUD sobre productos")
@RequiredArgsConstructor
@RequestMapping("/api/devolution")
@RestController
public class ControllerDevolution {
    private final IDeleteOperation deleteOperation;
    private final IGetOperations getOperations;
    private final IPostOperation postOperation;


    @Operation(summary = "Registrar una nueva devolucion")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Descripción de éxito"),
    @ApiResponse(responseCode = "400", description = "Solicitud inválida",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "401", description = "No autorizado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "404", description = "No encontrado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "409", description = "Conflicto",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid DtoDevolution dtoDevolution) {
        return postOperation.save(dtoDevolution);
    }



    @Operation(summary = "Buscar devoluciones por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Descripción de éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "No encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Conflicto",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return getOperations.findById(id);
    }


    @Operation(summary = "Listar todas las devoluciones registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Descripción de éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "No encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Conflicto",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        return getOperations.findAll();
    }


    @Operation(summary = "Eliminar devolucion por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Descripción de éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "No encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Conflicto",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        return deleteOperation.deleteById(id);
    }


    @Operation(summary = "Buscar devolucion por filtros opcionales (nombre de cliente, fecha, hora)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Descripción de éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "No encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Conflicto",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<?> findByFilters
        (
            @RequestParam(required = false) String nameProduct,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) LocalTime hour
        ){
        return getOperations.findByFilters(nameProduct, date, hour);
    }
}

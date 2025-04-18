package com.invetronix.backend.APIpurchases.controllers;

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
import com.invetronix.backend.APIpurchases.controllers.in.IDeleteOperation;
import com.invetronix.backend.APIpurchases.controllers.in.IGetOperations;
import com.invetronix.backend.APIpurchases.controllers.in.IPostOperation;
import com.invetronix.backend.APIpurchases.controllers.in.IPutOperation;
import com.invetronix.backend.APIpurchases.dtos.DtoPurchase;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Registro de compras", description = "API para operaciones CRUD sobre compras")
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
@RestController
public class ControllerPurchase {
    private final IDeleteOperation deleteOperation;
    private final IGetOperations getOperations;
    private final IPostOperation postOperation;
    private final IPutOperation putOperation;
    

    @Operation(summary = "Registrar una nueva compra")
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
    public ResponseEntity<?> save(@RequestBody @Valid DtoPurchase dtoPurchase) {
        return postOperation.save(dtoPurchase);
    }

    @Operation(summary = "Buscar compra por ID")
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


    @Operation(summary = "Listar todas las compras registradas")
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


    @Operation(summary = "Actualizar compra por ID")
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
    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id, @Valid @RequestBody DtoPurchase purchaseDto) {
        return putOperation.updateById(id, purchaseDto);
    }



    @Operation(summary = "Eliminar compra por ID")
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


    @Operation(summary = "Buscar compra por filtros opcionales (nombre de cliente, fecha, hora)")
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
            @RequestParam(required = false) String nameClient,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) LocalTime hour
        ){
        return getOperations.findByFilters(nameClient, date, hour);
    }

}
package com.invetronix.backend.APIGemini.controllers;

import com.invetronix.backend.APIGemini.services.IServiceGemini;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/predicciones")
@Tag(name = "Predicciones de Productos", description = "Endpoints para obtener predicciones de ventas")
public class ControllerGemini {
    
    private final IServiceGemini serviceGemini;

    public ControllerGemini(IServiceGemini serviceGemini) {
        this.serviceGemini = serviceGemini;
    }

    @Operation(
        summary = "Obtener predicción de ventas",
        description = "Obtiene un mensaje con la predicción de los productos más vendidos"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Predicción generada exitosamente"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })  
    @GetMapping
    public ResponseEntity<?> obtenerPrediccion() {
        try {
            String prediccion = serviceGemini.obtenerPrediccionVentas();
            return ResponseEntity.ok(Map.of("prediccion", prediccion));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                Map.of(
                    "error", "Error al procesar la solicitud",
                    "detalles", e.getMessage()
                )
            );
        }
    }
} 
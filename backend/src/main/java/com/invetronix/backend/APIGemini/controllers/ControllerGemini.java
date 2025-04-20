package com.invetronix.backend.APIGemini.controllers;

import com.invetronix.backend.APIGemini.services.IServiceGemini;
import com.invetronix.backend.APIGemini.services.ServiceGemini;
import com.invetronix.backend.APIproducts.models.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/predicciones")
@Tag(name = "Predicciones de Productos", description = "Endpoints para obtener predicciones de ventas usando Gemini AI")
public class ControllerGemini {
    
    private final IServiceGemini serviceGemini;

    public ControllerGemini(ServiceGemini serviceGemini) {
        this.serviceGemini = serviceGemini;
    }

    @Operation(
        summary = "Predecir productos más vendidos",
        description = "Obtiene una predicción basada en IA sobre qué productos tendrán mejor/menor desempeño en ventas",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Lista de productos a analizar",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Product.class)),
                examples = {
                    @ExampleObject(
                        name = "Ejemplo básico",
                        value = """
                        [
                          {
                            "id": "prod-001",
                            "name": "Laptop HP",
                            "description": "Laptop 15 pulgadas",
                            "price": 1500,
                            "category": "Electrónicos",
                            "stockQuantity": 10,
                            "supplier": "HP Inc"
                          }
                        ]
                        """
                    )
                }
            )
        )
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Predicción generada exitosamente",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = {
                    @ExampleObject(
                        value = """
                        Producto con mayor probabilidad de venderse más: Laptop HP (prod-001)
                        - Precio: $1500
                        - Stock: 10 unidades
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error en la solicitud",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = {
                    @ExampleObject(
                        value = """
                        {
                          "error": "Error al procesar la solicitud",
                          "detalles": "Error en la petición: 400 - Mensaje de error"
                        }
                        """
                    )
                }
            )
        )
    })
    @PostMapping
    public ResponseEntity<?> predecir(@RequestBody List<Product> products) {
        try {
            String respuesta = serviceGemini.predecirProductosMasVendidos(products);
            return ResponseEntity.ok(respuesta);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "error", "Error al procesar la solicitud",
                    "detalles", e.getMessage()
                )
            );
        }
    }
}
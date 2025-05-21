package com.invetronix.backend.APIimages.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.invetronix.backend.APIimages.services.IImageSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;

@RestController
@Tag(name = "Imagenes", description = "Endpoints para obtener imagenes de Unsplash")
@RequestMapping("/api/images")
public class ImageSearchController {
    private final IImageSearchService imageSearchService;

    @Autowired
    public ImageSearchController(IImageSearchService imageSearchService) {
        this.imageSearchService = imageSearchService;
    }

    @Operation(
        summary = "Buscar imagen en Unsplash",
        description = "Realiza una búsqueda de imagen basada en el texto proporcionado y devuelve la URL de la primera imagen encontrada."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagen encontrada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda no válido"),
        @ApiResponse(responseCode = "500", description = "Error interno al buscar la imagen")
    })
    @GetMapping
    public ResponseEntity<String> searchImage(
            @Parameter(description = "Texto para buscar imágenes") 
            @RequestParam String query
        ) {
        try {
            String result = imageSearchService.searchImage(query);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject()
                .put("status", "error")
                .put("message", "Error al buscar la imagen: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse.toString());
        }
    }
}
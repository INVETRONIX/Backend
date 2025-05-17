
package com.invetronix.backend.APIGemini.controller;

import com.invetronix.backend.APIGemini.model.GeminiResponse;
import com.invetronix.backend.APIGemini.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/gemini")
@Tag(name = "Gemini API", description = "API para análisis predictivo de ventas usando Gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @GetMapping("/predecir")
    @Operation(summary = "Analiza las ventas y proporciona predicciones", 
               description = "Utiliza Gemini para analizar las ventas y proporcionar predicciones sobre productos más y menos vendidos")
    public ResponseEntity<GeminiResponse> analizarVentas() {
        try {
            GeminiResponse response = geminiService.analizarVentas();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            GeminiResponse errorResponse = new GeminiResponse();
            errorResponse.setAnalisis("Error en el controlador: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
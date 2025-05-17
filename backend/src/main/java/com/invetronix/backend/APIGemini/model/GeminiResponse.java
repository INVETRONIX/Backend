package com.invetronix.backend.APIGemini.model;

import lombok.Data;

@Data
public class GeminiResponse {
    private String analisis;
    private String prediccion;
    private String recomendaciones;
} 
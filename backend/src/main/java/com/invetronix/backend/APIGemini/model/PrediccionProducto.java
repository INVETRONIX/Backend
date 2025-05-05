package com.invetronix.backend.APIGemini.model;

import com.invetronix.backend.APIproducts.model.Producto;
import lombok.Data;

@Data
public class PrediccionProducto {
    private Producto producto;
    private Double probabilidadVenta;
    private String tendencia; // "ALTA", "MEDIA" o "BAJA"
    private String razonamiento;
} 
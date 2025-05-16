package com.invetronix.backend.APIproductos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "productos")
@Schema(description = "Modelo de Producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Column(name = "nombre", nullable = false)
    @Schema(description = "Nombre del producto", example = "Producto 1", required = true)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    @Schema(description = "Descripción del producto", example = "Descripción del producto 1", required = true)
    private String descripcion;
    
    @Column(name = "precio", nullable = false)
    @Schema(description = "Precio del producto", example = "100.00", required = true)
    private double precio;

    @Column(name = "stock", nullable = false)
    @Schema(description = "Stock del producto", example = "10", required = true)
    private int stock;

}
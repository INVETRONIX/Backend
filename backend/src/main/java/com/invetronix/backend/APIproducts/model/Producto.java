package com.invetronix.backend.APIproducts.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productos")
@Schema(description = "Entidad que representa un producto en el sistema")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del producto", example = "Laptop HP")
    private String nombre;

    @Column(nullable = false, length = 1000)
    @Schema(description = "Descripción detallada del producto", example = "Laptop HP con procesador Intel i7, 16GB RAM, 512GB SSD")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Precio del producto", example = "999.99")
    private Double precio;

    @Column(nullable = false)
    @Schema(description = "Categoría del producto", example = "Electrónicos")
    private String categoria;

    @Column(nullable = false)
    @Schema(description = "Cantidad disponible en stock", example = "50")
    private Integer stock;

    @Column(nullable = false)
    @Schema(description = "Nombre del proveedor", example = "HP Inc.")
    private String proveedor;
} 
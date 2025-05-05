package com.invetronix.backend.APIcompras.model;

import jakarta.persistence.*;
import lombok.Data;
import com.invetronix.backend.APIproducts.model.Producto;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "detalles_compra")
public class DetalleCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;
} 
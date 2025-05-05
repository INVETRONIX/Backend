package com.invetronix.backend.APIdevoluciones.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import com.invetronix.backend.APIcompras.model.Compra;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "devoluciones")
public class Devolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    @Column(nullable = false)
    private LocalDate fechaDevolucion;

    @Column(nullable = false)
    private LocalTime horaDevolucion;

    @PrePersist
    protected void onCreate() {
        fechaDevolucion = LocalDate.now();
        horaDevolucion = LocalTime.now();
    }
} 
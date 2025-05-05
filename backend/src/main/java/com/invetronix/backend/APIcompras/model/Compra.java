package com.invetronix.backend.APIcompras.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.invetronix.backend.APIusers.model.Usuario;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "compras")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fechaCompra;

    @Column(nullable = false)
    private LocalTime horaCompra;

    @JsonManagedReference
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<DetalleCompra> detalles;

    @Column(nullable = false)
    private Double total = 0.0;

    @PrePersist
    protected void onCreate() {
        fechaCompra = LocalDate.now();
        horaCompra = LocalTime.now();
    }
}
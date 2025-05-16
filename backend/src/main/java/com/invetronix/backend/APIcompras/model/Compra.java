package com.invetronix.backend.APIcompras.model;

import java.time.LocalDate;
import java.time.LocalTime;
import com.invetronix.backend.APIproductos.model.Producto;
import com.invetronix.backend.APIusuarios.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compras")
@Schema(description = "Modelo de Compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la compra", example = "1")
    private Long id;

    @Column(name = "fecha", nullable = false)
    @Schema(description = "Fecha de la compra", example = "2021-01-01")
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    @Schema(description = "Hora de la compra", example = "12:00:00")
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuario que realizó la compra")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @Schema(description = "Producto comprado")
    private Producto producto;

    @PrePersist
    protected void onCreate() {
        fecha = LocalDate.now();
        hora = LocalTime.now().withSecond(0).withNano(0);
    }
}

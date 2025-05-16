package com.invetronix.backend.APIusuarios.model;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
@Schema(description = "Modelo de Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Column(name = "correo", nullable = false)
    @Schema(description = "Correo electrónico del usuario", example = "usuario@ejemplo.com", required = true)
    private String correo;

    @Column(name = "contrasena", nullable = false)
    @Schema(description = "Contraseña del usuario", example = "password123", required = true)
    private String contrasena;

    @Column(name = "nombre", nullable = false)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", required = true)
    private String nombre;

    @Column(name = "edad", nullable = false)
    @Schema(description = "Edad del usuario", example = "25", required = true)
    private int edad;

    @Column(name = "rol", nullable = false)
    @Schema(description = "Rol del usuario en el sistema", example = "CLIENTE", defaultValue = "CLIENTE")
    private String rol = "CLIENTE";
}
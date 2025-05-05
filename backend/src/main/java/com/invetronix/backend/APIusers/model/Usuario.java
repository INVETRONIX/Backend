package com.invetronix.backend.APIusers.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // Getters, Setters, Constructors, etc.
@Entity // Indica que es una entidad de JPA
@Table(name = "usuarios") // Indica el nombre de la tabla en la base de datos
public class Usuario {
    @Id // Indica que es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID se genera automáticamente
    private Long id;

    @Column(name = "nombre", nullable = false) // Indica que el nombre es obligatorio
    private String nombre;

    @Column(name = "edad", nullable = false) // Indica que la edad es obligatoria
    private int edad;

    @Column(name = "correo", nullable = false, unique = true) // Indica que el correo es obligatorio y debe ser único
    private String correo;

    @Column(name = "contrasena", nullable = false) // Indica que la contraseña es obligatoria
    private String contrasena;

    @Column(name = "telefono") // Indica que el teléfono es opcional
    private String telefono;
} 
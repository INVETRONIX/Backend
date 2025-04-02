package com.invetronix.backend.registroUsuario.models;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class Administrador extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String experiencia;
    private String sueldo;
}
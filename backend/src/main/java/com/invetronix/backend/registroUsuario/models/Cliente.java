package com.invetronix.backend.registroUsuario.models;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class Cliente extends User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String telefone;
    private int edad;
}
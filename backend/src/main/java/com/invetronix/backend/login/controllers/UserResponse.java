package com.invetronix.backend.login.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String nombre;
    private String email;
    private String tipoUsuario;
}
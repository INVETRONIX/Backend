package com.invetronix.backend.APIlogin.model;

import com.invetronix.backend.APIusuarios.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private Usuario usuario;
} 
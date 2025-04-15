package com.invetronix.backend.registroUsuario.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class Client{
    private String id;
    private String name;
    private String email;
    private String password;
    private Integer age;
    private String phone;
    
}
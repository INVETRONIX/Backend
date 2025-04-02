package com.invetronix.backend.registroUsuario.models;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class User implements Serializable{
    protected static final long serialVersionUID =1L;
    protected String id = UUID.randomUUID().toString();
    protected String name;
    protected String email;
    protected String password;
}
package com.invetronix.backend.registroUsuario.entities;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class EntityClient implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String email;
    private String password;
    private Integer age;
    private String phone;

    public EntityClient( String name, String email, String password, Integer age, String phone) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.phone = phone;
    }

}
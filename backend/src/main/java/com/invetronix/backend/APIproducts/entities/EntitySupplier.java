package com.invetronix.backend.APIproducts.entities;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class EntitySupplier implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id= UUID.randomUUID().toString();
    private String name;
    private String company;
    private String phone;
    private String email;

    public EntitySupplier(String name, String company, String phone, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.company = company;
        this.phone = phone;
        this.email = email;
    }

}
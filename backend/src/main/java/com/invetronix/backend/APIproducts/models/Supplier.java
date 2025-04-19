package com.invetronix.backend.APIproducts.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Supplier {
    private String id;
    private String name;
    private String email;
    private String company;
    private String phone; 
}

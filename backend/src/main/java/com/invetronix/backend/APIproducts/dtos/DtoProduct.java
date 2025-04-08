package com.invetronix.backend.APIproducts.dtos;

import com.invetronix.backend.APIsuppliers.dtos.DtoSupplier;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoProduct { 
    private String id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String name; 

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String description;

    @NotNull(message = "EL precio no debe estar vacio")
    @Min(value = 1, message = "El precio debe de ser positivo")
    private double price;

    @NotBlank(message = "La categoria no puede estar vacia")
    private String category;

    @NotNull(message = "El stock no puede estar vacio")
    @Min(value = 1, message = "El stock debe de ser positivo")
    private int stockQuantity;

    @Valid
    private DtoSupplier supplier;
}
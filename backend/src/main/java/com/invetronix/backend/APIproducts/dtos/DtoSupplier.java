package com.invetronix.backend.APIproducts.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoSupplier {
    private String id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;

    @NotBlank(message = "La empresa no puede estar vacia")
    private String company;

    @NotBlank(message = "El telefono no puede estar vacio")
    @Pattern(regexp = "^\\+57\\s?[0-9]{10}$", message = "El teléfono debe estar en formato +57 3104567890")
    private String phone;

    @Email(message = "Correo no es valido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;
}
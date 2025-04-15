package com.invetronix.backend.registroUsuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoClient{

    private String id;

    @NotBlank(message = "El telefono no puede estar vacio")
    private String name;

    @Email(message = "Correo no es valido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "la contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener minimo 6 caracteres")
    private String password;

    @NotNull(message = "La edad no puede estar vacia")
    @Min(value = 1, message = "La edad no puede ser negativa")
    private Integer age;

    @NotBlank(message = "El telefono no puede estar vacio")
    @Pattern(regexp = "^\\+57\\s?[0-9]{10}$", message = "El teléfono debe estar en formato +57 3104567890")
    private String phone;
}
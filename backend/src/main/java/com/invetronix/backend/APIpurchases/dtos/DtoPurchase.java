package com.invetronix.backend.APIpurchases.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.invetronix.backend.APIproducts.dtos.DtoProduct;
import com.invetronix.backend.registroUsuario.dto.DtoClient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder 
public class DtoPurchase {
    private String id;

    @Valid
    private DtoClient client;

    private String hour;

    private String date;

    @Valid
    @NotNull(message = "La lista de productos no puede ser nula")
    @NotEmpty(message = "La lista de productos no puede estar vacía")
    private List<DtoProduct> products;

    @NotNull(message = "EL total no debe estar vacio")
    @Min(value = 1, message = "El total debe de ser positivo")
    private double total;
}

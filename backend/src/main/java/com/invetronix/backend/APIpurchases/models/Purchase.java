package com.invetronix.backend.APIpurchases.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.invetronix.backend.APIproducts.models.Product;
import com.invetronix.backend.registroUsuario.models.Client;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor 
public class Purchase {
    private String id;
    private Client client;
    private LocalTime hour;
    private LocalDate date;
    private List<Product> products;
    private double total;
}
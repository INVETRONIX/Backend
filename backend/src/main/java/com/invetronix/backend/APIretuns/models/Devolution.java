package com.invetronix.backend.APIretuns.models;

import com.invetronix.backend.APIpurchases.models.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Devolution {
    private String id;
    private Purchase purchase;
}
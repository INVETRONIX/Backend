package com.invetronix.backend.APIretuns.dtos;

import com.invetronix.backend.APIpurchases.dtos.DtoPurchase;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoDevolution {
    private String id;

    @Valid
    private DtoPurchase purchase;

    private String date;
}
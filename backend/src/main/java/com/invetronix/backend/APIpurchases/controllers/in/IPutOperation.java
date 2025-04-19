package com.invetronix.backend.APIpurchases.controllers.in;

import org.springframework.http.ResponseEntity;
import com.invetronix.backend.APIpurchases.dtos.DtoPurchase;

public interface IPutOperation {
    ResponseEntity<?> updateById(String id, DtoPurchase dtoPurchase);
}
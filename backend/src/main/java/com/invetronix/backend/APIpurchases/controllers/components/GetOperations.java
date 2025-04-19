package com.invetronix.backend.APIpurchases.controllers.components;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIpurchases.controllers.in.IGetOperations;
import com.invetronix.backend.APIpurchases.dtos.DtoPurchase;
import com.invetronix.backend.APIpurchases.exceptions.PurchaseNotFoundException;
import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.APIpurchases.services.ServicePurchase;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component("getOperationPurchase")
@RequiredArgsConstructor
public class GetOperations implements IGetOperations{
    private final ServicePurchase servicePurchase;

    @Override
    public ResponseEntity<?> findById(String id) {
        try {
            return servicePurchase.findById(id)
                .map(purchase -> new ResponseEntity<>(MapperPurchase.toDto(purchase), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: producto no encontrado después de validación"));
        } catch (PurchaseNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        try {
            List<Purchase> purchases = servicePurchase.findAll();
            List<DtoPurchase> purchasesDto = purchases.stream()
                    .map(MapperPurchase::toDto)
                    .toList();

            return purchasesDto.isEmpty() 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(purchasesDto, HttpStatus.OK);
        } catch (PurchaseNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND); 
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findByFilters(String nameCliente, LocalDate date, LocalTime hour) {
        try {
            List<Purchase> products = servicePurchase.findByFilters(nameCliente, date, hour);
            List<DtoPurchase> productsDto = products.stream()
                    .map(MapperPurchase::toDto) 
                    .toList();

            return productsDto.isEmpty() 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(productsDto, HttpStatus.OK);
        } catch (PurchaseNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

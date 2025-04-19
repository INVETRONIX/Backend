package com.invetronix.backend.APIpurchases.services.in;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.invetronix.backend.APIpurchases.models.Purchase;

public interface IFindPurchasesByFilters {
    List<Purchase> findByFilters(String nameClient, LocalDate date, LocalTime hour);
}
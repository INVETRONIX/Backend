package com.invetronix.backend.APIpurchases.services.in;

import java.util.Optional;
import com.invetronix.backend.APIpurchases.models.Purchase;

public interface IFindPurchaseById {
    Optional<Purchase> findById(String id);
}
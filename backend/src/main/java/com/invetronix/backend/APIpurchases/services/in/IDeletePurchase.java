package com.invetronix.backend.APIpurchases.services.in;

import java.util.Optional;
import com.invetronix.backend.APIpurchases.models.Purchase;

public interface IDeletePurchase {
    Optional<Purchase> deleteById(String id);
}
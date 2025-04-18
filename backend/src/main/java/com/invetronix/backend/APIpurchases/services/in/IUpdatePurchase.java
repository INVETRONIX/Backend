package com.invetronix.backend.APIpurchases.services.in;

import java.util.Optional;
import com.invetronix.backend.APIpurchases.models.Purchase;

public interface IUpdatePurchase {
    Optional<Purchase> updateById(String id, Purchase purch);
}
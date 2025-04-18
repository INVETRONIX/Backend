package com.invetronix.backend.APIpurchases.services.in;

import com.invetronix.backend.APIpurchases.models.Purchase;

public interface ISavePurchase {
    Purchase save(Purchase purchase);
}
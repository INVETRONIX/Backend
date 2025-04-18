package com.invetronix.backend.APIpurchases.services.in;

import java.util.List;
import com.invetronix.backend.APIpurchases.models.Purchase;

public interface IFindAllPurchases {
    List<Purchase> findAll();
}
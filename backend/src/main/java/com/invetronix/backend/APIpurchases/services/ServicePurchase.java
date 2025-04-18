package com.invetronix.backend.APIpurchases.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.APIpurchases.services.in.IDeletePurchase;
import com.invetronix.backend.APIpurchases.services.in.IFindAllPurchases;
import com.invetronix.backend.APIpurchases.services.in.IFindPurchaseById;
import com.invetronix.backend.APIpurchases.services.in.IFindPurchasesByFilters;
import com.invetronix.backend.APIpurchases.services.in.ISavePurchase;
import com.invetronix.backend.APIpurchases.services.in.IUpdatePurchase;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicePurchase implements IDeletePurchase, IFindAllPurchases, IFindPurchaseById, IFindPurchasesByFilters, ISavePurchase, IUpdatePurchase{

    private final IDeletePurchase deletePurchase;
    private final IFindAllPurchases findAllPurchases;
    private final IFindPurchaseById findPurchaseById;
    private final IFindPurchasesByFilters findPurchasesByFilters;
    private final ISavePurchase savePurchase;
    private final IUpdatePurchase updatePurchase;


    @Override
    public Optional<Purchase> updateById(String id, Purchase purch) {
        return updatePurchase.updateById(id, purch);
    }

    @Override
    public Purchase save(Purchase purchase) {
        return savePurchase.save(purchase);
    }

    @Override
    public List<Purchase> findByFilters(String nameClient, LocalDate date, LocalTime hour) {
        return findPurchasesByFilters.findByFilters(nameClient, date, hour);
    }

    @Override
    public Optional<Purchase> findById(String id) {
        return findPurchaseById.findById(id);
    }

    @Override
    public List<Purchase> findAll() {
        return findAllPurchases.findAll();
    }

    @Override
    public Optional<Purchase> deleteById(String id) {
        return deletePurchase.deleteById(id);
    }
    
}

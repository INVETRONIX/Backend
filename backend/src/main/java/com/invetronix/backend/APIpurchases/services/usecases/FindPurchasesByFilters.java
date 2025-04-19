package com.invetronix.backend.APIpurchases.services.usecases;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIpurchases.entities.EntityPurchase;
import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.APIpurchases.repositories.RepositoryPurchase;
import com.invetronix.backend.APIpurchases.services.in.IFindPurchasesByFilters;

public class FindPurchasesByFilters implements IFindPurchasesByFilters{
    private final RepositoryPurchase repositoryPurchase;

    @Autowired
    public FindPurchasesByFilters(RepositoryPurchase repositoryPurchase) {
        this.repositoryPurchase = repositoryPurchase;
    }

    @Override
    public List<Purchase> findByFilters(String nameClient, LocalDate date, LocalTime hour) {
        List<EntityPurchase> entities = repositoryPurchase.findByFilters(
            nameClient !=null ? nameClient.toLowerCase() :null, 
            date, 
            hour
        );
        
        return entities.stream()
            .map(MapperPurchase::toModel)
            .collect(Collectors.toList());
    }
    
}

package com.invetronix.backend.APIpurchases.services.usecases;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIpurchases.mappers.MapperPurchase;
import com.invetronix.backend.APIpurchases.models.Purchase;
import com.invetronix.backend.APIpurchases.repositories.RepositoryPurchase;
import com.invetronix.backend.APIpurchases.services.in.IFindAllPurchases;

public class FindAllPurchases implements IFindAllPurchases{
    private final RepositoryPurchase repositoryPurchase;
    
    @Autowired
    public FindAllPurchases(RepositoryPurchase repositoryPurchase) {
        this.repositoryPurchase = repositoryPurchase;
    }

    @Override
    public List<Purchase> findAll() {
        return repositoryPurchase.findAll().stream()
            .map(MapperPurchase::toModel)
            .collect(Collectors.toList());
    }
    
}
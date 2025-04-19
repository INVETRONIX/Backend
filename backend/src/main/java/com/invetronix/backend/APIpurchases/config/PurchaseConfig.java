package com.invetronix.backend.APIpurchases.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.invetronix.backend.APIpurchases.repositories.RepositoryPurchase;
import com.invetronix.backend.APIpurchases.services.ServicePurchase;
import com.invetronix.backend.APIpurchases.services.in.IValidateServicePurchase;
import com.invetronix.backend.APIpurchases.services.usecases.DeletePurchase;
import com.invetronix.backend.APIpurchases.services.usecases.FindAllPurchases;
import com.invetronix.backend.APIpurchases.services.usecases.FindPurchaseById;
import com.invetronix.backend.APIpurchases.services.usecases.FindPurchasesByFilters;
import com.invetronix.backend.APIpurchases.services.usecases.SavePurchase;
import com.invetronix.backend.APIpurchases.services.usecases.UpdatePurchase;

@Configuration
public class PurchaseConfig {

    @Bean
    public ServicePurchase servicePurchase(RepositoryPurchase repositoryPurchase, IValidateServicePurchase validateServicePurchase) {
        return new ServicePurchase(
            new DeletePurchase(repositoryPurchase, validateServicePurchase), 
            new FindAllPurchases(repositoryPurchase), 
            new FindPurchaseById(repositoryPurchase, validateServicePurchase), 
            new FindPurchasesByFilters(repositoryPurchase), 
            new SavePurchase(repositoryPurchase), 
            new UpdatePurchase(repositoryPurchase, validateServicePurchase)
        );
        
    }
}

package com.invetronix.backend.APIproducts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.invetronix.backend.APIproducts.repositories.RepositoryProduct;
import com.invetronix.backend.APIproducts.services.ServiceProduct;
import com.invetronix.backend.APIproducts.services.in.IValidationServiceProduct;
import com.invetronix.backend.APIproducts.services.usecases.DeleteProduct;
import com.invetronix.backend.APIproducts.services.usecases.FindAllProducts;
import com.invetronix.backend.APIproducts.services.usecases.FindProductById;
import com.invetronix.backend.APIproducts.services.usecases.FindProductsByFilters;
import com.invetronix.backend.APIproducts.services.usecases.SaveProduct;
import com.invetronix.backend.APIproducts.services.usecases.UpdateProduct;

@Configuration
public class ProductConfig {
    
    @Bean
    public ServiceProduct serviceProduct(RepositoryProduct repositoryProduct, IValidationServiceProduct validationServiceProduct) {
        return new ServiceProduct(
            new DeleteProduct(repositoryProduct, validationServiceProduct), 
            new FindAllProducts(repositoryProduct),
            new FindProductById(repositoryProduct, validationServiceProduct), 
            new FindProductsByFilters(repositoryProduct), 
            new SaveProduct(repositoryProduct), 
            new UpdateProduct(repositoryProduct, validationServiceProduct)
        );
    }
}

package com.invetronix.backend.APIretuns.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.invetronix.backend.APIretuns.repositories.RepositoryDevolution;
import com.invetronix.backend.APIretuns.services.ServiceDevolution;
import com.invetronix.backend.APIretuns.services.in.IValidateServiceDevolution;
import com.invetronix.backend.APIretuns.services.usecases.DeleteDevolution;
import com.invetronix.backend.APIretuns.services.usecases.FindAllDevolutions;
import com.invetronix.backend.APIretuns.services.usecases.FindDevolutionById;
import com.invetronix.backend.APIretuns.services.usecases.FindDevolutionsByFilters;
import com.invetronix.backend.APIretuns.services.usecases.SaveDevolution;

@Configuration
public class ReturnConfig {

    @Bean
    public ServiceDevolution serviceDevolution(RepositoryDevolution repositoryDevolution, IValidateServiceDevolution validateServiceDevolution) {
        return new ServiceDevolution(
            new DeleteDevolution(repositoryDevolution, validateServiceDevolution), 
            new FindAllDevolutions(repositoryDevolution), 
            new FindDevolutionById(repositoryDevolution, validateServiceDevolution), 
            new FindDevolutionsByFilters(repositoryDevolution), 
            new SaveDevolution(repositoryDevolution)
        );
    }
}
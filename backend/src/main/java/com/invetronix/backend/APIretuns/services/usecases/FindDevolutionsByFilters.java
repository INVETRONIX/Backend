package com.invetronix.backend.APIretuns.services.usecases;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIretuns.entities.EntityDevolution;
import com.invetronix.backend.APIretuns.mappers.MapperDevolution;
import com.invetronix.backend.APIretuns.models.Devolution;
import com.invetronix.backend.APIretuns.repositories.RepositoryDevolution;
import com.invetronix.backend.APIretuns.services.in.IFindDevolutionsByFilters;

public class FindDevolutionsByFilters implements IFindDevolutionsByFilters{
    private final RepositoryDevolution repositoryDevolution;

    @Autowired
    public FindDevolutionsByFilters(RepositoryDevolution repositoryDevolution) {
        this.repositoryDevolution = repositoryDevolution;
    }

    @Override
    public List<Devolution> findByFilters(String nameClient, LocalDate date, LocalTime hour) {
         List<EntityDevolution> entities = repositoryDevolution.findByFilters(
            nameClient !=null ? nameClient.toLowerCase() :null, 
            date, 
            hour
        );
        
        return entities.stream()
            .map(MapperDevolution::toModel)
            .collect(Collectors.toList());
    }
    
}
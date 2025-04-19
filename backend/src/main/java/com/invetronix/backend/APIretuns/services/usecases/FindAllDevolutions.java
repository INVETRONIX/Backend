package com.invetronix.backend.APIretuns.services.usecases;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIretuns.mappers.MapperDevolution;
import com.invetronix.backend.APIretuns.models.Devolution;
import com.invetronix.backend.APIretuns.repositories.RepositoryDevolution;
import com.invetronix.backend.APIretuns.services.in.IFindAllDevolutions;

public class FindAllDevolutions implements IFindAllDevolutions{
    private final RepositoryDevolution repositoryDevolution;

    @Autowired
    public FindAllDevolutions(RepositoryDevolution repositoryDevolution) {
        this.repositoryDevolution = repositoryDevolution;
    }

    @Override
    public List<Devolution> findAll() {
       return repositoryDevolution.findAll().stream()
            .map(MapperDevolution::toModel)
            .collect(Collectors.toList());
    }
    
}
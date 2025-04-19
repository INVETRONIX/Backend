package com.invetronix.backend.APIretuns.services.usecases;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIretuns.entities.EntityDevolution;
import com.invetronix.backend.APIretuns.mappers.MapperDevolution;
import com.invetronix.backend.APIretuns.models.Devolution;
import com.invetronix.backend.APIretuns.repositories.RepositoryDevolution;
import com.invetronix.backend.APIretuns.services.in.IFindDevolutionById;
import com.invetronix.backend.APIretuns.services.in.IValidateServiceDevolution;

public class FindDevolutionById implements IFindDevolutionById{
    private final RepositoryDevolution repositoryDevolution;
    private final IValidateServiceDevolution validateServiceDevolution;

    @Autowired
    public FindDevolutionById(RepositoryDevolution repositoryDevolution,
            IValidateServiceDevolution validateServiceDevolution) {
        this.repositoryDevolution = repositoryDevolution;
        this.validateServiceDevolution = validateServiceDevolution;
    }

    @Override
    public Optional<Devolution> findById(String id) {
       Optional<EntityDevolution> entity = repositoryDevolution.findById(id);
       validateServiceDevolution.validateDevolutionExist(entity, id);
        return Optional.of(MapperDevolution.toModel(entity.get()));
    }
    
}
package com.invetronix.backend.APIretuns.services.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import com.invetronix.backend.APIretuns.entities.EntityDevolution;
import com.invetronix.backend.APIretuns.mappers.MapperDevolution;
import com.invetronix.backend.APIretuns.models.Devolution;
import com.invetronix.backend.APIretuns.repositories.RepositoryDevolution;
import com.invetronix.backend.APIretuns.services.in.ISaveDevolution;

public class SaveDevolution implements ISaveDevolution{
    private final RepositoryDevolution repositoryDevolution;

    @Autowired
    public SaveDevolution(RepositoryDevolution repositoryDevolution) {
        this.repositoryDevolution = repositoryDevolution;
    }

    @Override
    public Devolution save(Devolution devolution) {
        devolution.getPurchase().getClient().setName(devolution.getPurchase().getClient().getName().toLowerCase());
        EntityDevolution entityDevolution = MapperDevolution.toEntity(devolution);
        return MapperDevolution.toModel(repositoryDevolution.save(entityDevolution));
    }
    
}
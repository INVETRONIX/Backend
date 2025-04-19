package com.invetronix.backend.APIretuns.services.usecases;

import java.util.Optional;
import com.invetronix.backend.APIretuns.entities.EntityDevolution;
import com.invetronix.backend.APIretuns.exceptions.DevolutionNotFoundException;
import com.invetronix.backend.APIretuns.services.in.IValidateServiceDevolution;

public class ValidateServiceDevolution implements IValidateServiceDevolution{

    @Override
    public void validateDevolutionExist(Optional<EntityDevolution> entity, String id) {
        if(!entity.isPresent()){
            throw new DevolutionNotFoundException("La devolucion con el id: "+id+" no existe");
        }
    }
    
}

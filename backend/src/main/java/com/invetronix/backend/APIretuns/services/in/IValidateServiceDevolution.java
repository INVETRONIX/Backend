package com.invetronix.backend.APIretuns.services.in;

import java.util.Optional;
import com.invetronix.backend.APIretuns.entities.EntityDevolution;

public interface IValidateServiceDevolution {
    void validateDevolutionExist(Optional<EntityDevolution> entity, String id);
}
package com.invetronix.backend.APIretuns.services.in;

import java.util.Optional;
import com.invetronix.backend.APIretuns.models.Devolution;

public interface IDeleteDevolution {
    Optional<Devolution> deleteById(String id);
}
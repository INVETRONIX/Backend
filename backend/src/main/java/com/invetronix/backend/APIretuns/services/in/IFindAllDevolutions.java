package com.invetronix.backend.APIretuns.services.in;

import java.util.List;
import com.invetronix.backend.APIretuns.models.Devolution;

public interface IFindAllDevolutions {
    List<Devolution> findAll();
}
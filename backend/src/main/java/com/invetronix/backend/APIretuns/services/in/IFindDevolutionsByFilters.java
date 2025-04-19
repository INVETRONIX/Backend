package com.invetronix.backend.APIretuns.services.in;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.invetronix.backend.APIretuns.models.Devolution;

public interface IFindDevolutionsByFilters {
     List<Devolution> findByFilters(String nameClient, LocalDate date, LocalTime hour);
}
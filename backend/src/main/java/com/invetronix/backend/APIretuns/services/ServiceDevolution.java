package com.invetronix.backend.APIretuns.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIretuns.models.Devolution;
import com.invetronix.backend.APIretuns.services.in.IDeleteDevolution;
import com.invetronix.backend.APIretuns.services.in.IFindAllDevolutions;
import com.invetronix.backend.APIretuns.services.in.IFindDevolutionById;
import com.invetronix.backend.APIretuns.services.in.IFindDevolutionsByFilters;
import com.invetronix.backend.APIretuns.services.in.ISaveDevolution;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceDevolution implements IDeleteDevolution, IFindAllDevolutions, IFindDevolutionById, IFindDevolutionsByFilters, ISaveDevolution{
    private final IDeleteDevolution deleteDevolution;
    private final IFindAllDevolutions findAllDevolutions;
    private final IFindDevolutionById findDevolutionById;
    private final IFindDevolutionsByFilters findDevolutionsByFilters;
    private final ISaveDevolution saveDevolution;


    @Override
    public Devolution save(Devolution devolution) {
        return saveDevolution.save(devolution);
    }

    @Override
    public List<Devolution> findByFilters(String nameClient, LocalDate date, LocalTime hour) {
        return findDevolutionsByFilters.findByFilters(nameClient, date, hour);
    }

    @Override
    public Optional<Devolution> findById(String id) {
        return findDevolutionById.findById(id);
    }

    @Override
    public List<Devolution> findAll() {
        return findAllDevolutions.findAll();
    }

    @Override
    public Optional<Devolution> deleteById(String id) {
        return deleteDevolution.deleteById(id);
    }
    
}
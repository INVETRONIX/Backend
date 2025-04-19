package com.invetronix.backend.APIretuns.controllers.components;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIretuns.controllers.in.IGetOperations;
import com.invetronix.backend.APIretuns.dtos.DtoDevolution;
import com.invetronix.backend.APIretuns.exceptions.DevolutionNotFoundException;
import com.invetronix.backend.APIretuns.mappers.MapperDevolution;
import com.invetronix.backend.APIretuns.models.Devolution;
import com.invetronix.backend.APIretuns.services.ServiceDevolution;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetOperations implements IGetOperations{
    private final ServiceDevolution serviceDevolution;

    @Override
    public ResponseEntity<?> findById(String id) {
        try {
            return serviceDevolution.findById(id)
                .map(devolution -> new ResponseEntity<>(MapperDevolution.toDto(devolution), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: producto no encontrado después de validación"));
        } catch (DevolutionNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
         try {
            List<Devolution> devolution = serviceDevolution.findAll();
            List<DtoDevolution> devolutionDto = devolution.stream()
                    .map(MapperDevolution::toDto)
                    .toList();

            return devolutionDto.isEmpty() 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(devolutionDto, HttpStatus.OK);
        } catch (DevolutionNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND); 
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findByFilters(String nameClient, LocalDate date, LocalTime hour) {
        try {
            List<Devolution> devolutions = serviceDevolution.findByFilters(nameClient, date, hour);
            List<DtoDevolution> devolutionDto = devolutions.stream()
                    .map(MapperDevolution::toDto) 
                    .toList();

            return devolutionDto.isEmpty() 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(devolutionDto, HttpStatus.OK);
        } catch (DevolutionNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

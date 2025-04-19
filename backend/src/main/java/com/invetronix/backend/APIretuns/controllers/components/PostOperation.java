package com.invetronix.backend.APIretuns.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIretuns.controllers.in.IPostOperation;
import com.invetronix.backend.APIretuns.dtos.DtoDevolution;
import com.invetronix.backend.APIretuns.mappers.MapperDevolution;
import com.invetronix.backend.APIretuns.models.Devolution;
import com.invetronix.backend.APIretuns.services.ServiceDevolution;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostOperation implements IPostOperation{
    private final ServiceDevolution serviceDevolution;

    @Override
    public ResponseEntity<?> save(DtoDevolution dtoDevolution) {
         try {
            Devolution devolution = MapperDevolution.toModel(dtoDevolution);
            Devolution saved = serviceDevolution.save(devolution);
            DtoDevolution devolutionSaved = MapperDevolution.toDto(saved);
            return new ResponseEntity<>(devolutionSaved, HttpStatus.CREATED);
    
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

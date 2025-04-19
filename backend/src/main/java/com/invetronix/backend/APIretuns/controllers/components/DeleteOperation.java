package com.invetronix.backend.APIretuns.controllers.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIretuns.controllers.in.IDeleteOperation;
import com.invetronix.backend.APIretuns.exceptions.DevolutionNotFoundException;
import com.invetronix.backend.APIretuns.mappers.MapperDevolution;
import com.invetronix.backend.APIretuns.services.ServiceDevolution;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Component("deleteOperationReturn")
@RequiredArgsConstructor
public class DeleteOperation implements IDeleteOperation{
    private final ServiceDevolution serviceDevolution;

    @Override
    public ResponseEntity<?> deleteById(String id) {
         try {
            return serviceDevolution.deleteById(id)
                .map(devolution -> new ResponseEntity<>(MapperDevolution.toDto(devolution), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: No se pudo eliminar el producto"));
        } catch (DevolutionNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
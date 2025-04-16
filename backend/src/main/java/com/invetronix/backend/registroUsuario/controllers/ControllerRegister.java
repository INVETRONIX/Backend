package com.invetronix.backend.registroUsuario.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.invetronix.backend.registroUsuario.dto.DtoClient;
import com.invetronix.backend.registroUsuario.exceptions.UserAlreadyRegisteredException;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.registroUsuario.services.IServiceRegister;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/register")
@RestController
public class ControllerRegister {
    private final IServiceRegister serviceRegister;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody DtoClient dtoUser) {
         try {

            Client user= MapperUser.toModel(dtoUser);

            Client saved = serviceRegister.save(user);

            DtoClient userSaved = MapperUser.toDto(saved);

            return new ResponseEntity<>(userSaved, HttpStatus.CREATED);

        } catch (UserAlreadyRegisteredException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

        }catch (RuntimeException e){

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
        @PathVariable String id
    ) {
        try {
            return serviceRegister.findById(id)
                .map(user -> new ResponseEntity<>(MapperUser.toDto(user), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: usuario no encontrado después de validación"));
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   
    @GetMapping("/by-email/{email}")
    public ResponseEntity<?> findByEmail(
        @PathVariable String email
    ) {
        try {
            return serviceRegister.findByEmail(email)
                .map(user -> new ResponseEntity<>(MapperUser.toDto(user), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: usuario no encontrado después de validación"));
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Client> users = serviceRegister.findAll();
            List<DtoClient> usersDto = users.stream()
                    .map(MapperUser::toDto)
                    .toList();

            return usersDto.isEmpty() 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(usersDto, HttpStatus.OK);
        } catch (UserNotFoundException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND); 
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
    }

    
    @PutMapping("updateById/{id}")
    public ResponseEntity<?> update(
        @PathVariable String id,
        @Valid @RequestBody DtoClient userDto
    ) {
        try {
            Client userUpdate = MapperUser.toModel(userDto);
            return serviceRegister.updateById(id, userUpdate)
                .map(user -> new ResponseEntity<>(MapperUser.toDto(user), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: usuario no encontrado después de validación"));
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (UserAlreadyRegisteredException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateByEmail/{email}")
    public ResponseEntity<?> updateByEmail(@PathVariable String email, @Valid @RequestBody DtoClient userDto){
        try {
            Client userUpdate = MapperUser.toModel(userDto);
            return serviceRegister.updateByEmail(email, userUpdate)
            .map(user -> new ResponseEntity<>(MapperUser.toDto(user), HttpStatus.OK))
            .orElseThrow(() -> new RuntimeException("Error inesperado: usuario no encontrado después de validación"));
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (UserAlreadyRegisteredException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteById(
        @PathVariable String id
    ) {
        try {
            return serviceRegister.deleteById(id)
                .map(user -> new ResponseEntity<>(MapperUser.toDto(user), HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("Error inesperado: usuario no encontrado después de validación"));
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public ResponseEntity<?> deleteByEmail(@PathVariable String email) {
        try {
            return serviceRegister.deleteByEmail(email)
            .map(user -> new ResponseEntity<>(MapperUser.toDto(user), HttpStatus.OK))
            .orElseThrow(() -> new RuntimeException("Error inesperado: usuario no encontrado después de validación"));
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/search")
    public ResponseEntity<?> findByFilters(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email
    ) {
        try {
            List<Client> users = serviceRegister.findByFilters(name, email);
            List<DtoClient> usersDto = users.stream()
                    .map(MapperUser::toDto)
                    .toList();

            return usersDto.isEmpty() 
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(usersDto, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/auth")
    public ResponseEntity<?> findByAuthToken(@RequestHeader("Authorization") String token) {
        boolean isValid = serviceRegister.findByAuthToken(token);
        String message = isValid ? "Token válido" : "Token inválido";
        return new ResponseEntity<>(message, isValid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }
}

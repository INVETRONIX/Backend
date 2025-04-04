package com.invetronix.backend.registroUsuario.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.invetronix.backend.registroUsuario.exceptions.UserAlreadyRegisteredException;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.models.User;
import com.invetronix.backend.registroUsuario.services.IClienteService;
import com.invetronix.backend.shared.controllers.ErrorResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
@RequestMapping("api/usuarios")
public class ClienteController {
    private final IClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user){
        
        try {

            User userSaved = clienteService.save(user);
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
    public ResponseEntity<?> findById(@PathVariable String id){

        try {

            return clienteService.findById(id)
            .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElseThrow(() -> new RuntimeException("Unexpected error: bus not found after validation."));

        } catch (UserNotFoundException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

        } catch(RuntimeException e){

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<User> clientes = clienteService.findAll();
            if (!clientes.isEmpty()) {
                return new ResponseEntity<>(clientes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
            }
        } catch (UserNotFoundException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND); 
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
    }
}
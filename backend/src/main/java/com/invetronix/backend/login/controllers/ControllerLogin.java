package com.invetronix.backend.login.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invetronix.backend.login.exceptions.InvalidPasswordException;
import com.invetronix.backend.login.services.IServiceLogin;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.mappers.MapperUser;
import com.invetronix.backend.registroUsuario.models.Client;
import com.invetronix.backend.shared.controllers.ErrorResponse;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RequestMapping("/login")
@RestController
public class ControllerLogin {
    private final IServiceLogin serviceLogin;
    
    @GetMapping
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password){
        try {
            Optional <Client> user = serviceLogin.login(email, password);

            return user
            .map(u -> new ResponseEntity<>(MapperUser.toDto(u), HttpStatus.OK))
            .orElseThrow(() -> new RuntimeException("Error inesperado, durante el login"));

        } catch (UserNotFoundException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch(InvalidPasswordException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}

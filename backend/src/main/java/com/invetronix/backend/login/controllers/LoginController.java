package com.invetronix.backend.login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.invetronix.backend.registroUsuario.controllers.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.invetronix.backend.login.exceptions.InvalidCredentialsException;
import com.invetronix.backend.login.services.LoginService;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.models.User;

@RestController
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
 
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("correo") String correo, 
                        @RequestParam("contraseña") String contraseña) {

            try {
                User response = loginService.validateUser(correo, contraseña);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (UserNotFoundException e) {
    
                ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

            } catch (InvalidCredentialsException e) {
    
                ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
                return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);

            } catch (RuntimeException e) {
                
                ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

            }

    }
}
// ajustar realmente en RequestParam o usar PathVariable
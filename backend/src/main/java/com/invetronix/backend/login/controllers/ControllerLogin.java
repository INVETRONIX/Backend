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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor; 

@Tag(name = "Login", description = "API para el manejo de autenticación de usuarios")
@RequiredArgsConstructor
@RequestMapping("/login")
@RestController
public class ControllerLogin {
    private final IServiceLogin serviceLogin;
    
    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario autenticarse con su correo y contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado o contraseña incorrecta"),
        @ApiResponse(responseCode = "401", description = "Error inesperado durante el login")
    })
    @GetMapping
    public ResponseEntity<?> login(
            @Parameter(description = "Correo electrónico del usuario", required = true)
            @RequestParam String email,

            @Parameter(description = "Contraseña del usuario", required = true)
            @RequestParam String password
        ){
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
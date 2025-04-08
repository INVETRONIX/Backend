package com.invetronix.backend.login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.invetronix.backend.login.exceptions.InvalidCredentialsException;
import com.invetronix.backend.login.services.LoginService;
import com.invetronix.backend.registroUsuario.exceptions.UserNotFoundException;
import com.invetronix.backend.registroUsuario.models.User;
import com.invetronix.backend.shared.controllers.ErrorResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Autenticación", description = "Operaciones relacionadas con el login de usuarios")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
 
    
   @Operation(
        summary = "Autenticar usuario",
        description = "Valida las credenciales de un usuario y devuelve sus datos si son correctos. "
            + "Nota: En producción, considera usar POST para operaciones de login por seguridad."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Autenticación exitosa",
            content = @Content(schema = @Schema(implementation = User.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Credenciales inválidas",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping("/login")
    public ResponseEntity<?> login( @Parameter(
        description = "Email del usuario",
        required = true,
        example = "usuario@example.com"
    )
    @RequestParam("email") String correo,
    
    @Parameter(
        description = "Contraseña del usuario",
        required = true,
        example = "MiContraseñaSegura123"
    )
    @RequestParam("password") String contraseña) {

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
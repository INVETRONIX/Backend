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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Gestión de Usuarios", description = "Operaciones CRUD para la gestión de usuarios")
@RestController
@RequiredArgsConstructor 
@RequestMapping("/api/usuarios")
public class ClienteController {
    private final IClienteService clienteService;

    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea un nuevo usuario en el sistema",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del usuario a registrar",
            required = true,
            content = @Content(schema = @Schema(implementation = User.class))
    ))
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = User.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "El usuario ya está registrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
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



    @Operation(
        summary = "Obtener usuario por ID",
        description = "Recupera los detalles de un usuario específico mediante su ID único"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = User.class))
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
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
        @Parameter(
            description = "ID único del usuario",
            required = true,
            example = "550e8400-e29b-41d4-a716-446655440000"
        )
    @PathVariable String id){

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


    @Operation(
        summary = "Listar todos los usuarios",
        description = "Recupera una lista completa de todos los usuarios registrados en el sistema"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de usuarios encontrados",
            content = @Content(schema = @Schema(implementation = User[].class))
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No hay usuarios registrados"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron usuarios",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
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
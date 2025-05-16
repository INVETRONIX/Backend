package com.invetronix.backend.APIusuarios.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.invetronix.backend.APIusuarios.model.Usuario;
import com.invetronix.backend.APIusuarios.service.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "API para la gestión de usuarios")
public class UsuarioController {

    @Autowired
    private final IUsuarioService usuarioService;

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "409", description = "El correo ya está registrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
        Usuario saved = usuarioService.create(usuario);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = usuarioService.getAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(
        @Parameter(description = "ID del usuario a buscar") @PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(
        @Parameter(description = "ID del usuario a actualizar") @PathVariable Long id,
        @RequestBody Usuario usuario) {
        Optional<Usuario> updated = usuarioService.update(id, usuario);
        return updated.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID del usuario a eliminar") @PathVariable Long id) {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @Operation(summary = "Buscar usuarios por nombre", description = "Retorna usuarios que coincidan con el nombre proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios")
    })
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Usuario>> findByNombre(
        @Parameter(description = "Nombre a buscar") @RequestParam String nombre) {
        List<Usuario> usuarios = usuarioService.findByNombre(nombre);
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    @Operation(summary = "Buscar usuarios por edad", description = "Retorna usuarios que coincidan con la edad proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios")
    })
    @GetMapping("/buscar/edad")
    public ResponseEntity<List<Usuario>> findByEdad(
        @Parameter(description = "Edad a buscar") @RequestParam Integer edad) {
        List<Usuario> usuarios = usuarioService.findByEdad(edad);
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    @Operation(summary = "Buscar usuarios por filtros", description = "Retorna usuarios que coincidan con los filtros proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> findByFilters(
        @Parameter(description = "Nombre a buscar (opcional)") @RequestParam(required = false) String nombre,
        @Parameter(description = "Edad a buscar (opcional)") @RequestParam(required = false) Integer edad) {
        List<Usuario> usuarios = usuarioService.findByFilters(nombre, edad);
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}
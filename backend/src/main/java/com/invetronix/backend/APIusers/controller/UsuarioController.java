package com.invetronix.backend.APIusers.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.invetronix.backend.APIusers.model.Usuario;
import com.invetronix.backend.APIusers.service.IUsuarioService;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para gestionar usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Operation(summary = "Crear nuevo usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "409", description = "El correo electrónico ya está registrado")
    })
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(
            @Parameter(description = "Datos del usuario a crear")
            @RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "409", description = "El correo electrónico ya está registrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del usuario")
            @RequestBody Usuario usuarioDetails) {
        Usuario updatedUsuario = usuarioService.updateUsuario(id, usuarioDetails);
        return ResponseEntity.ok(updatedUsuario);
    }

    @Operation(summary = "Eliminar usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar", example = "1")
            @PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios encontrada")
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(
            @Parameter(description = "ID del usuario a buscar", example = "1")
            @PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Buscar usuarios por filtros")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios encontrada")
    @GetMapping("/filtros")
    public ResponseEntity<List<Usuario>> findByFilters(
            @Parameter(description = "Nombre a buscar", example = "Juan")
            @RequestParam(required = false) String nombre,
            @Parameter(description = "Correo a buscar", example = "juan@example.com")
            @RequestParam(required = false) String correo,
            @Parameter(description = "Edad a buscar", example = "25")
            @RequestParam(required = false) Integer edad) {
        List<Usuario> usuarios = usuarioService.findByFilters(nombre, correo, edad);
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Buscar usuarios por nombre")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios encontrada")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> findByNombre(
            @Parameter(description = "Nombre a buscar", example = "Juan")
            @PathVariable String nombre) {
        List<Usuario> usuarios = usuarioService.findByNombre(nombre);
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Buscar usuarios por correo")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios encontrada")
    @GetMapping("/correo/{correo}")
    public ResponseEntity<List<Usuario>> findByCorreo(
            @Parameter(description = "Correo a buscar", example = "juan@example.com")
            @PathVariable String correo) {
        List<Usuario> usuarios = usuarioService.findByCorreo(correo);
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Buscar usuarios por edad")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios encontrada")
    @GetMapping("/edad/{edad}")
    public ResponseEntity<List<Usuario>> findByEdad(
            @Parameter(description = "Edad a buscar", example = "25")
            @PathVariable Integer edad) {
        List<Usuario> usuarios = usuarioService.findByEdad(edad);
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }
} 
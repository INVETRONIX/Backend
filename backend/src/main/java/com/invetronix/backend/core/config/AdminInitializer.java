package com.invetronix.backend.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.invetronix.backend.APIusuarios.model.Usuario;
import com.invetronix.backend.APIusuarios.repository.UsuarioRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existe un admin
        if (!usuarioRepository.getByCorreo("admin@invetronix.com").isPresent()) {
            Usuario admin = Usuario.builder()
                    .nombre("Administrador")
                    .correo("admin@invetronix.com")
                    .contrasena(passwordEncoder.encode("admin123"))
                    .edad(30)
                    .rol("ADMIN")
                    .build();
            
            usuarioRepository.save(admin);
            System.out.println("Usuario administrador creado exitosamente");
        }
    }
} 
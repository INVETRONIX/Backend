package com.invetronix.backend.APIlogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.invetronix.backend.APIlogin.model.LoginRequest;
import com.invetronix.backend.APIlogin.model.LoginResponse;
import com.invetronix.backend.APIusuarios.model.Usuario;
import com.invetronix.backend.APIusuarios.repository.UsuarioRepository;
import com.invetronix.backend.APIlogin.exception.InvalidPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService implements ILoginService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.getByCorreo(loginRequest.getCorreo())
                .orElseThrow(() -> new InvalidPassword("Credenciales inválidas"));

        if (!passwordEncoder.matches(loginRequest.getContrasena(), usuario.getContrasena())) {
            throw new InvalidPassword("Credenciales inválidas");
        }

        String token = generateToken(usuario);
        return new LoginResponse(token, usuario);
    }

    private String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("correo", usuario.getCorreo());
        claims.put("rol", usuario.getRol());

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getCorreo())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }
}
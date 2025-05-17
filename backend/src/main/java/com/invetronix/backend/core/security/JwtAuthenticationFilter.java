package com.invetronix.backend.core.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invetronix.backend.core.exception.NoTokenException;
import com.invetronix.backend.core.exception.NoAdminAccessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // Permitir acceso libre solo a endpoints específicos
        if (requestURI.equals("/api/usuarios") && method.equals("POST") ||
            requestURI.equals("/api/auth/login") ||
            requestURI.startsWith("/api/compras")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NoTokenException();
        }

        try {
            String jwt = authHeader.substring(7);
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            String correo = claims.getSubject();
            String rol = claims.get("rol", String.class);

            // Verificar si el endpoint requiere rol ADMIN
            if ((requestURI.startsWith("/api/productos") || 
                 requestURI.startsWith("/api/gemini") ||
                 requestURI.startsWith("/api/images") ||
                 (requestURI.startsWith("/api/usuarios") && !method.equals("POST"))) 
                && !rol.equals("ADMIN")) {
                throw new NoAdminAccessException();
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    correo,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + rol))
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
            
        } catch (NoTokenException | NoAdminAccessException e) {
            throw e;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            Map<String, String> error = Map.of("error", "Token inválido");
            new ObjectMapper().writeValue(response.getOutputStream(), error);
            return;
        }

        filterChain.doFilter(request, response);
    }
} 
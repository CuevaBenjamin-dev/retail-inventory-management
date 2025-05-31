package com.retail.inventory.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta para firmar el token (en producción usar variable de entorno o vault)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Tiempo de validez del token (ejemplo: 1 hora)
    private final long expirationMillis = 3600000;

    // Generar token con username y rol como claims
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key)
                .compact();
    }

    // Extraer username del token
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // Extraer rol del token
    public String extractRole(String token) {
        return (String) parseClaims(token).get("role");
    }

    // Validar si el token es válido y no está expirado
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Parsear claims del token
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

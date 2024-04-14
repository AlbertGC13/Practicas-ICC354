package com.jismah.proyecto2.servicios;

import com.jismah.proyecto2.encapsulaciones.AuthResponse;
import com.jismah.proyecto2.entidades.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWT_Service {

    @Value("${token.jwt}")
    private String SECRET;

    @Value("${token.tiempo}")
    private int tiempoExpirar;
    private Logger logger =  LoggerFactory.getLogger(JWT_Service.class);

    private SecurityService usuarioService;

    public JWT_Service(SecurityService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public AuthResponse generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();

        //Obtiendo los roles del usuarios
        Usuario byUsuario = usuarioService.findByUsername(userName);
        claims.put("roles", byUsuario.getRol());
        //
        return createToken(claims, userName);
    }

    private AuthResponse createToken(Map<String, Object> claims, String userName) {
        logger.info("Generando JWT para el usuario: "+userName);
        //Generando la fecha valida
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(tiempoExpirar);
        logger.info("La fecha actual: "+localDateTime.toString());
        //
        Date fechaExpiracion = Date.from(localDateTime.toInstant(ZoneOffset.ofHours(-4)));

        //Generando el GWT
        String jwt =  Jwts.builder()
                .setIssuer("PWA-JWT")
                .setClaims(claims)
                .setSubject(userName)
                .setExpiration(fechaExpiracion)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

        return new AuthResponse(jwt, fechaExpiracion.getTime());
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractClaim(String token, String nombre){
        Claims claims = extractAllClaims(token);
        return claims.get(nombre, String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }
}

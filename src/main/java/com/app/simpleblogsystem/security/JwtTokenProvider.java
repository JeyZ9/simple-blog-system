package com.app.simpleblogsystem.security;

import com.app.simpleblogsystem.exception.AppException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);
        String roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .claim("roles", roles)
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64URL.decode(jwtSecret)
        );
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }catch(MalformedJwtException ex){
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }catch(ExpiredJwtException ex){
            throw new AppException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }catch(UnsupportedJwtException ex){
            throw new AppException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }catch(IllegalArgumentException ex){
            throw new AppException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}

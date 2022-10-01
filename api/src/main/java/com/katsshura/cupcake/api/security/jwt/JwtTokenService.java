package com.katsshura.cupcake.api.security.jwt;

import com.katsshura.cupcake.api.security.dto.UserDetailsDTO;
import com.katsshura.cupcake.core.config.jwt.JwtProperties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class JwtTokenService {
    private final JwtProperties jwtProperties;

    public JwtTokenService(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateJwtToken(Authentication authentication) {

        final var userPrincipal = (UserDetailsDTO) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getEmail()))
                .addClaims(this.getClaims(userPrincipal))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtProperties.getExpirationMs()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }

    public String getUserEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private Map<String, Object> getClaims(final UserDetailsDTO userDetailsDTO) {
        return Map.of(
                "id", userDetailsDTO.getId(),
                "name", userDetailsDTO.getName()
        );
    }
}

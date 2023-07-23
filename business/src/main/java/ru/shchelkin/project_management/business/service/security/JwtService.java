package ru.shchelkin.project_management.business.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shchelkin.project_management.commons.exceptions.auth.JwtInvalidException;

import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.lifetime}")
    private Duration lifetime;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public String generateToken(String login) {
        return generateToken(Collections.emptyMap(), login);
    }


    public String generateToken(Map<String, Object> extraClaims, String login) {
        final Date now = new Date();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(login)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + lifetime.toMillis()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (RuntimeException ex) {
            throw new JwtInvalidException("Jwt is not valid", ex);
        }
    }

    private Key getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

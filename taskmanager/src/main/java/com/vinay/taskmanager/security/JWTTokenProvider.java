package com.vinay.taskmanager.security;

import com.vinay.taskmanager.exception.APIException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private final SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication auth){
        String email = auth.getName();
        Date currTime = new Date();
        Date expireTime = new Date(currTime.getTime() + 3600000);
        String token = Jwts
                .builder()
                .subject(email)
                .issuedAt(currTime)
                .expiration(expireTime)
                .signWith(jwtSecretKey)
                .compact();
        return token;
    }

    public String getEmailFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new APIException("Token issue :" + e.getMessage());
        }
    }
}

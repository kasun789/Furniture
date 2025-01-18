package com.falcon.furniture.furniture.security.jwt;

import com.falcon.furniture.furniture.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${security.jwt.secret.key}")
    private String securityKey;
    @Value("${security.jwt.expiration.time}")
    private Long expirationTime;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrinciple = (UserDetailsImpl) authentication.getPrincipal();
        Date expirationDate = new Date((new Date()).getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(userPrinciple.getEmail())
                .claim("roles", userPrinciple.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }


    public Key key(){
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));
    }

    public String getUsernameFromJwtToken(String token) {
        try {
            String userName = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .setAllowedClockSkewSeconds(60)  // Allow 60 seconds of clock skew
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return userName;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expired at {}. Current time: {}.", e.getClaims().getExpiration(), new Date());
            return null;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("getUserNameFromJwtToken ---> {}", e.getMessage());
            return null;
        }
    }



    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .setAllowedClockSkewSeconds(60)  // Allow 60 seconds of clock skew
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e){
            logger.error("Invalid Jwt Token ---> {}", e.getMessage());
        } catch (ExpiredJwtException e){
            logger.error("Jwt token is expired ---> {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("Jwt token Unsupported ---> {}", e.getMessage());
        } catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty ---> {}", e.getMessage());
        }
        return false;
    }


    public String getRolesFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", String.class);
    }

}

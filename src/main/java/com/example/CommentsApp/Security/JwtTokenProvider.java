package com.example.CommentsApp.Security;



import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${commentsapp.app.secret}")
    private String APP_SECRET;
    @Value("${commentsapp.expires.in}")
    private Long EXPIRES_IN;

    public String generateJwtToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }

    Long getUserIdFromJwt(String token) {
        Claims claims = getJwsClaims(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    boolean validateToken(String token){
        try{
            getJwsClaims(token);
            return !isTokenExpired(token);
        }catch(SignatureException e){
            return  false;
        }catch(MalformedJwtException e){
            return  false;
        }catch(ExpiredJwtException e){
            return  false;
        }catch(UnsupportedJwtException e){
            return  false;
        }catch(IllegalArgumentException e){
            return  false;
        }

    }

    private boolean isTokenExpired(String token) {
        Date expiration = getJwsClaims(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

    private Jws<Claims> getJwsClaims(String token){
        return Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
    }
}

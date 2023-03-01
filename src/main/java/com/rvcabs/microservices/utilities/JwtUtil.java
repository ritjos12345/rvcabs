package com.rvcabs.microservices.utilities;

import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Map;
import java.util.HashMap;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Jwts;
import java.util.function.Function;
import java.util.Date;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil
{
    public String extractUsername(final String token) {
        return this.extractClaim(token, Claims::getSubject);
    }
    
    public Date extractExpiration(final String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }
    
    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(final String token) {
        return (Claims)Jwts.parser().setSigningKey("CpoxyPASS!@#").parseClaimsJws(token).getBody();
    }
    
    private Boolean isTokenExpired(final String token) {
        return this.extractExpiration(token).before(new Date());
    }
    
    public String generateToken(final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<String, Object>();
        return this.createToken(claims, userDetails.getUsername());
    }
    
    private String createToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder().setClaims((Map)claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 72000000L)).signWith(SignatureAlgorithm.HS256, "CpoxyPASS!@#").compact();
    }
    
    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }
}
package com.rvcabs.microservices.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rvcabs.microservices.dto.TokenDto;
import com.rvcabs.microservices.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {

	private static final Logger logger = Logger.getLogger(JwtGenerator.class.getName());

    public ResponseEntity<?> generate(JwtUser jwtUser) {
		return null;
		/*
		 * try { TokenDto tokenDto = new TokenDto(); //builder Claims claims =
		 * Jwts.claims() .setSubject(jwtUser.getUserName()); claims.put("accountId",
		 * String.valueOf(jwtUser.getAccountId())); claims.put("role","ADMIN");
		 * //claims.put("grantedAuthorities",
		 * (List<GrantedAuthority>)jwtUser.getGrantedAuthorities()); LocalDateTime ldt =
		 * LocalDateTime.now().plusMinutes(JwtKeyConstants.TOKEN_EXPIRY_TIME);
		 * ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault()); Date expirationTime =
		 * Date.from(zdt.toInstant()); String token = Jwts.builder() .setClaims(claims)
		 * .setIssuer(JwtKeyConstants.ISSUER) .signWith(SignatureAlgorithm.HS512,
		 * JwtKeyConstants.SIGN_IN_KEY) .setExpiration(expirationTime)
		 * .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).
		 * toInstant())) //not big token,its very small .compact();
		 * tokenDto.setToken(token); //tokenDto.setExpires(expirationTime); return
		 * ResponseEntity.status(HttpStatus.OK).eTag("Token").body(tokenDto); } catch
		 * (Exception e) { e.printStackTrace(); logger.log(Level.SEVERE,
		 * "Error : Generating and getting token: " + e.fillInStackTrace()); return
		 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).eTag("Error").body(
		 * null); }
		 */
    }
}

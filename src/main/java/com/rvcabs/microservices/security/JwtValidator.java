package com.rvcabs.microservices.security;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rvcabs.microservices.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtValidator {

    public JwtUser validate(String token) {
        JwtUser jwtUser = null;
    
        	try {
				//parse the token
				Claims body = Jwts.parser()
						//use secret key
						.requireIssuer(JwtKeyConstants.ISSUER)
				        .setSigningKey(JwtKeyConstants.SIGN_IN_KEY)
				        .parseClaimsJws(token)
				        //header,payload(body),signature
				        .getBody();
				jwtUser = new JwtUser(body.getSubject(),(String) body.get("accountId"));
				//body.getSubject() = username
				//jwtUser.setUserName(body.getSubject());
				//userId
				//jwtUser.setAccountId((String) body.get("accountId"));
         //role
			//	jwtUser.setRole((String) body.get("role"));

				@SuppressWarnings("unchecked")
				final List<Map<String,String>> authoritiesList = (List<Map<String,String>>) body.get("grantedAuthorities", List.class);
			/*
			 * final List<GrantedAuthority> auths = authoritiesList.stream() .map(t->new
			 * SimpleGrantedAuthority(t.get("authority"))) .collect(Collectors.toList());
			 */
				//jwtUser.setGrantedAuthorities(auths);

			} catch (ExpiredJwtException e) {
				//e.printStackTrace();
				throw new RuntimeException("Token Expired");
				
			} catch (UnsupportedJwtException e) {
				throw new RuntimeException("Invalid Token");
			} catch (MalformedJwtException e) {
				throw new RuntimeException("Invalid Token");
			} catch (SignatureException e) {
				throw new RuntimeException("Invalid Token");
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Invalid Token");
			}
  

        return jwtUser;
    }
}
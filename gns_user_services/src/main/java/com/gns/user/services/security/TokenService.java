package com.gns.user.services.security;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.gns.user.services.constants.UserServicesConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenService {

	private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

	public String addAuthentication(String userName) {
		logger.info("userName:::" + userName);
		String jwtToken = Jwts.builder().setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + UserServicesConstants.EXTIRPATION))
				.signWith(SignatureAlgorithm.HS512, UserServicesConstants.SECRET).compact();
		return UserServicesConstants.TOKEN_PREFIX + " " + jwtToken;
	}

	UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		logger.info("UsernamePasswordAuthenticationToken:::");
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		logger.info("token:::" + token);
		if (token != null) {
			Jws<Claims> jwtToken = Jwts.parser().setSigningKey(UserServicesConstants.SECRET)
					.parseClaimsJws(token.replace(UserServicesConstants.TOKEN_PREFIX, ""));
			Date expirationDate = jwtToken.getBody().getExpiration();
			long expirationTime = expirationDate.getTime();
			Date currentDate = new Date();
			long currentTime = currentDate.getTime();
			if (currentTime > expirationTime) {
				return null;
			}
			String user = jwtToken.getBody().getSubject();
			return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
		}
		return null;
	}

}

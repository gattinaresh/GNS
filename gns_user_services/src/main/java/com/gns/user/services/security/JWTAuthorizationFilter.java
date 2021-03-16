package com.gns.user.services.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.gns.user.services.constants.UserServicesConstants;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private TokenService tokenService;

	public JWTAuthorizationFilter(AuthenticationManager authManager, ApplicationContext applicationContext) {
		super(authManager);
		tokenService = applicationContext.getBean(TokenService.class);

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("JWTAuthorizationFilter:::");
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null || !header.startsWith(UserServicesConstants.TOKEN_PREFIX)) {
			SecurityContextHolder.getContext().setAuthentication(null);
			chain.doFilter(request, response);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(tokenService.getAuthentication(request));
		chain.doFilter(request, response);
	}

}

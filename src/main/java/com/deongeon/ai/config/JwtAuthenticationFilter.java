package com.deongeon.ai.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider tokenProvider;

	public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = resolveToken(request);

		if (token != null && tokenProvider.validateToken(token)) {
			Authentication auth = tokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		filterChain.doFilter(request, response);
	}

	// Authorization 헤더에서 Bearer 토큰 추출
	private String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");

		if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}
}

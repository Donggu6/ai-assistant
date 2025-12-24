package com.deongeon.ai.auth.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter; // 요청 1번당 1번 실행

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	// 토큰 생성 + 검증 담당
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 모든 요청이 여기 거쳐감
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
			throws ServletException, IOException {

		// 1️⃣ 헤더에서 토큰 꺼냄
		String token = jwtTokenProvider.resolveToken(request);

		// 2️⃣ 토큰이 있고, 유효하고, 아직 인증 안되어 있으면
		if (token != null &&
		    jwtTokenProvider.validateToken(token) &&
		    SecurityContextHolder.getContext().getAuthentication() == null) {

			// 3️⃣ Authentication 객체 생성
			Authentication auth = jwtTokenProvider.getAuthentication(token);

			// 4️⃣ 인증 정보를 SecurityContext 에 저장
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		// 5️⃣ 다음 필터로 진행
		filterChain.doFilter(request, response);
	}
}

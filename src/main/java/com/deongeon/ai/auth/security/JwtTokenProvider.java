package com.deongeon.ai.auth.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	@Value("${app.jwt.secret}")
	private String secretKeyString;

	@Value("${app.jwt.access-token-validity-ms}")
	private long validityInMs;

	private final CustomUserDetailsService userDetailsService;

	private Key secretKey; // 🔥 진짜 Key 객체

	/**
	 * 앱 시작할 때 1번만 실행
	 * String → Key 로 변환
	 */
	@PostConstruct
	public void initKey() {
		this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * JWT 생성
	 */
	public String createToken(String email) {
		Date now = new Date();
		Date expire = new Date(now.getTime() + validityInMs);

		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(now)
				.setExpiration(expire)
				.signWith(secretKey, SignatureAlgorithm.HS256) // ✅ 올바른 방식
				.compact();
	}

	/**
	 * 요청 헤더에서 토큰 추출
	 */
	public String resolveToken(HttpServletRequest req) {
		String bearer = req.getHeader("Authorization");
		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}

	/**
	 * 토큰 유효성 검사
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 토큰 → 인증 객체 변환
	 */
	public Authentication getAuthentication(String token) {
		String email = getEmail(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(
				userDetails,
				"",
				userDetails.getAuthorities()
		);
	}

	/**
	 * 토큰에서 email 가져오기
	 */
	public String getEmail(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}
}

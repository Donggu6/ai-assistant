package com.deongeon.ai.config;

import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.deongeon.ai.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration-ms}")
	private long validityInMs;

	// JWT 토큰 생성
	public String createToken(User user) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + validityInMs);

		return Jwts.builder().setSubject(user.getEmail()).claim("role", user.getRole()).setIssuedAt(now)
				.setExpiration(expiry).signWith(SignatureAlgorithm.HS256, secretKey.getBytes()).compact();
	}

	// JWT 토큰이 유효한지 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// JWT에서 email(subject) 가져오기
	public String getEmail(String token) {
		return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().getSubject();
	}

	// JWT에서 role 가져오기
	public String getRole(String token) {
		Object role = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().get("role");
		return role != null ? role.toString() : null;
	}

	// Authentication 객체로 변환
	public Authentication getAuthentication(String token) {
		String email = getEmail(token);
		String role = getRole(token);

		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role != null ? role : "ROLE_USER");

		return new UsernamePasswordAuthenticationToken(email, "", Collections.singletonList(authority));
	}
}

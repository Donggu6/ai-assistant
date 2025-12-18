package com.deongeon.ai.security;

import com.deongeon.ai.domain.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

	private final String SECRET_KEY = "ai-assistant-secret-key"; // 나중에 yml로 이동
	private final long EXPIRATION = 1000 * 60 * 60; // 1시간

	public String createToken(Long userId, Role role) {
		return Jwts.builder().setSubject(String.valueOf(userId)).claim("role", role.name()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
}

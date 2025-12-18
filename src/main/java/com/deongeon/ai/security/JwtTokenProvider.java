package com.deongeon.ai.security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	// TODO: 실전에서는 환경 변수나 설정 파일에서 불러오기
	private final String secretKey = "my-secret-key-for-jwt-should-be-long";

	public String createToken(String email) {
		return Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public String getEmail(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
}

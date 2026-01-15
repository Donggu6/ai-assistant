package com.deongeon.ai.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

	// ⚠️ 운영에서는 환경변수/설정으로 빼야 함
	private final SecretKey secretKey = Keys
			.hmacShaKeyFor("my-super-secret-key-1234567890123456".getBytes(StandardCharsets.UTF_8));

	private final long ACCESS_EXP = 1000L * 60 * 60 * 24; // 24h
	private final long TWO_FA_EXP = 1000L * 60 * 5; // 5m

	public String createAccessToken(String email, String plan) {
		return Jwts.builder().setSubject(email).claim("type", "ACCESS").claim("plan", plan).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXP))
				.signWith(secretKey, SignatureAlgorithm.HS256).compact();
	}

	public String createTwoFactorToken(String email) {
		return Jwts.builder().setSubject(email).claim("type", "PENDING_2FA").setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + TWO_FA_EXP))
				.signWith(secretKey, SignatureAlgorithm.HS256).compact();
	}

	public Claims parseClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getEmail(String token) {
		return parseClaims(token).getSubject();
	}

	public String getType(String token) {
		Object v = parseClaims(token).get("type");
		return v == null ? null : v.toString();
	}

	public String getPlan(String token) {
		Object v = parseClaims(token).get("plan");
		return v == null ? "FREE" : v.toString();
	}
	
	
}

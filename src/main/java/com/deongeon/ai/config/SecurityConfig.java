package com.deongeon.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtTokenProvider);

		http
				// CSRF 비활성화 + CORS 기본
				.csrf(csrf -> csrf.disable()).cors(cors -> {
				})
				// 세션을 쓰지 않는(JWT만 사용하는) 방식
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// URL별 접근 권한 설정
				.authorizeHttpRequests(auth -> auth
						// 회원가입 / 로그인은 누구나 가능
						.requestMatchers("/api/auth/**").permitAll()
						// 간단 테스트용 엔드포인트
						.requestMatchers("/api/ai/test").permitAll()
						// 헬스체크 같은 것도 열어두고 싶으면 여기에 추가
						.requestMatchers("/actuator/health").permitAll()
						// 그 외 나머지는 인증 필요
						.anyRequest().authenticated())
				// UsernamePasswordAuthenticationFilter 전에 JWT 필터 추가
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// 비밀번호 암호화용
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// AuthenticationManager (필요할 때 주입해서 사용)
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}

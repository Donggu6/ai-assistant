package com.deongeon.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.deongeon.ai.auth.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity // @PreAuthorize 같은 권한 애너테이션 사용 가능
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()) // JWT 사용 시 CSRF 필요 없음
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth

				// 🔓 인증 없이 접근 허용
				.requestMatchers(
						"/",
						"/login",
						"/register",
						"/api/auth/**",
						"/css/**",
						"/js/**"
				).permitAll()

				// 🔒 관리자 전용
				.requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")

				// 🔒 그 외 모든 요청은 인증 필요
				.anyRequest().authenticated()
			)

			// UsernamePasswordAuthenticationFilter 실행 전에 JWT 필터 실행
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// 비밀번호 암호화 빈
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

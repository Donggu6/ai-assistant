package com.deongeon.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.deongeon.ai.auth.jwt.JwtAuthenticationFilter;
import com.deongeon.ai.auth.oauth.CustomOAuth2UserService;
import com.deongeon.ai.auth.oauth.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(sm ->
            sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        );

        http.authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/", "/index.html", "/login.html", "/register.html",
                "/about.html", "/features.html", "/plan-description.html",
                "/2fa.html", "/forgot-password.html", "/reset-password.html",
                "/assets/**", "/js/**", "/css/**",
                "/oauth2/**", "/login/oauth2/**",
                "/api/auth/**", "/api/2fa/**"
            ).permitAll()

            .requestMatchers(
                "/dashboard.html", "/quote.html", "/quote-form.html",
                "/profit.html", "/settings.html",
                "/api/user/**", "/api/analysis/**"
            ).authenticated()

            .requestMatchers("/admin/**").hasRole("ADMIN")

            .anyRequest().permitAll()
        );

        http.exceptionHandling(eh -> eh.authenticationEntryPoint((req, res, ex) -> {
            res.sendRedirect("/login.html");
        }));

        // 🔐 일반 로그인
        http.formLogin(form -> form
            .loginPage("/login.html")
            .permitAll()
        );

        // 🌐 OAuth2 로그인
        http.oauth2Login(oauth -> oauth
            .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
            .successHandler(oAuth2SuccessHandler)
        );

        // 🚪 로그아웃
        http.logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login.html")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID", "ACCESS_TOKEN")
        );

        // 🔑 JWT 필터
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

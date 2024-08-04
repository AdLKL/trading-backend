package com.ad.trading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // This means that the application will not use HTTP sessions to store the authentication state,
                // which is typical for RESTful APIs where each request should be stateless and carry its own
                // authentication information (like a JWT token).
                .authorizeHttpRequests(Authorize -> Authorize.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                // This line adds a custom filter, JwtTokenValidator, before the BasicAuthenticationFilter
                // in the filter chain. This custom filter is responsible for validating JWT tokens before the request
                // reaches the basic authentication mechanism.
                .csrf(AbstractHttpConfigurer::disable)
                // Disables Cross-Site Request Forgery (CSRF) protection. This is common in stateless REST APIs because
                // CSRF protection is usually more relevant for stateful web applications.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {

        return request -> {
            CorsConfiguration cfg = new CorsConfiguration();
            cfg.setAllowedOrigins(
                    Arrays.asList("http://localhost:5173", "http://localhost:3000")
            );
            cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            cfg.setAllowCredentials(true);
            cfg.setExposedHeaders(List.of("Authorization"));
            cfg.setAllowedHeaders(Collections.singletonList("*"));
            cfg.setMaxAge(3600L);
            return cfg;
        };
    }
}

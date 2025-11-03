package com.mindguard.backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration

public class SecurityConfig {

	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable())
	            .cors(cors -> cors.configurationSource(request -> {
	                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
	                corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
	                corsConfig.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
	                corsConfig.setAllowedHeaders(List.of("*"));
	               // corsConfig.setExposedHeaders(List.of("Authorization", "Set-Cookie"));
	                corsConfig.setAllowCredentials(true);
	                
	                return corsConfig;
	            }))
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/auth/**").permitAll()
	                .requestMatchers("/api/quizzes/**").permitAll()
	                .requestMatchers("/api/audio/**").permitAll()
	                .requestMatchers("/api/chat/**").permitAll() // Allow chat endpoints
	                .requestMatchers("/api/mood/**").permitAll()
	                .requestMatchers("/api/mood/history").authenticated() // Anyone logged in can view
	             // Audio Therapy
	                .requestMatchers("/api/audiotherapy/**").permitAll()
	                .anyRequest().authenticated()
	            )
	            
	            .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	            );
	       
	        return http.build();
	    }

}

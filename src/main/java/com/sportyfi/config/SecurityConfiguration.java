package com.sportyfi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

//    private final AuthenticationProvider authenticationProvider;
//
//    public SecurityConfiguration(
//        AuthenticationProvider authenticationProvider
//    ) {
//        this.authenticationProvider = authenticationProvider;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
		.cors().and()
		.csrf().disable() // Disable CSRF for API calls
		.authorizeRequests()
        .requestMatchers("/sportyfi/auth/**", "/sportyfi/*", "/sportyfi/matches/*", "/sportyfi/match/**", "/sportyfi/venues/**", "/sportyfi/venues/verification/**",
        		"/sportyfi/venues/approve/**", "/sportyfi/participants/*", "/sportyfi/match/update/**", "/sportyfi/profiles/**", "/sportyfi/profiles/update/**",
        		"/sportyfi/deleteParticipant/*", "/ws/orders").permitAll()
        .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:8081", "http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}



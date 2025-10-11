package com.sportyfi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sportyfi.dao.SportyfiUserDao;
//import com.sportyfi.services.JwtService;
import com.sportyfi.services.JwtService;

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
	
//	private final JwtService jwtService;
//    private SportyfiUserDao userDao;
//
//    public SecurityConfiguration(JwtService jwtService, SportyfiUserDao userDao) {
//        this.jwtService = jwtService;
//        this.userDao = userDao;
//    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter(jwtService, userDao);
//    }
	
//	private final JwtService jwtService;
//    private final UserDetailsService userDetailsService;
//
//    public SecurityConfiguration(JwtService jwtService, UserDetailsService userDetailsService) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    	http
//		.cors().and()
//		.csrf().disable() // Disable CSRF for API calls
//		.authorizeRequests()
//        .requestMatchers(
//    		    "/sportyfi/auth/**", "/sportyfi/*", "/sportyfi/matches/*", 
//        		"/sportyfi/match/**", "/sportyfi/venues/**", "/sportyfi/venues/verification/**",
//        		"/sportyfi/venues/approve/**", "/sportyfi/bookings/**", "/sportyfi/bookings/cancel/**", 
//        		"/sportyfi/participants/*", 
//        		"/sportyfi/match/update/**", "/sportyfi/profiles/**", "/sportyfi/profiles/update/**",
//        		"/sportyfi/deleteParticipant/*", "/ws/orders"
//    	).permitAll()
//        .anyRequest().authenticated();
//    	JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
    	http
        .cors().and()
        .csrf().disable()
        .authorizeHttpRequests()//auth -> auth
            .requestMatchers(
//            		"/sportyfi/auth/**",
//                    "/sportyfi/*",
//                    "/sportyfi/matches/*",
//                    "/sportyfi/match/**",
//                    "/sportyfi/venues/**",
//                    "/sportyfi/venues/verification/**",
//                    "/sportyfi/venues/approve/**",
//                    "/sportyfi/profiles/**",
//                    "/ws/orders"
            		"/sportyfi/auth/**", "/sportyfi/**", "/sportyfi/matches/*",
            		"/sportyfi/match/**", "/sportyfi/venues/**", "/sportyfi/venues/verification/**",
            		"/sportyfi/venues/approve/**", "/sportyfi/venues/reject/**", 
            		"/sportyfi/bookings/**", "/sportyfi/userbookings/cancel/**", 
            		"/sportyfi/participants/*", 
            		"/sportyfi/venueowners/**", "/sportyfi/venueowners/bookings/**",
            		"/sportyfi/match/update/**", "/sportyfi/profiles/**", "/sportyfi/profiles/update/**",
            		"/sportyfi/deleteParticipant/*", "/ws/orders"
            ).permitAll()
            .anyRequest().authenticated()
            //.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
//        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

//        );
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:8081", "http://localhost:5173",
        		"http://52.54.102.100:8081", "https://52.54.102.100:8080", "https://sportyfiapp.com", "http://sportyfiapp.com"));
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



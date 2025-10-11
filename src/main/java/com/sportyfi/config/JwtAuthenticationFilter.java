//package com.sportyfi.config;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.UUID;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.sportyfi.dao.SportyfiUserDao; // your DAO that can find by email or id
//import com.sportyfi.dto.UserDto;
//import com.sportyfi.entity.Users;
//import com.sportyfi.services.JwtService;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final SportyfiUserDao userDao;
//
//    public JwtAuthenticationFilter(JwtService jwtService, SportyfiUserDao userDao) {
//        this.jwtService = jwtService;
//        this.userDao = userDao;
//    }
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain chain
//    ) throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String token = authHeader.substring(7);
//
//        try {
//            if (!jwtService.isTokenValid(token)) {
//                chain.doFilter(request, response);
//                return;
//            }
//
//            // Pull what you stored in the token (id/email)
//            String userIdStr = jwtService.extractUserId(token);         // you implemented this
//            String email     = jwtService.extractClaim(token, c -> c.get("email", String.class));
//
//            Users user = null;
//            if (userIdStr != null) {
//                try {
//                    user = userDao.findById(UUID.fromString(userIdStr));
//                } catch (Exception ignored) {}
//            }
//            if (user == null && email != null) {
//                user = userDao.findByEmail(email);
//            }
//            if (user == null) {
//                chain.doFilter(request, response);
//                return;
//            }
//
//            // Build UserDto principal
//            UserDto principal = new UserDto();
//            principal.setId(user.getId());
////            principal.setName(user.getName());
//            principal.setEmail(user.getEmail());
//            principal.setUserType(user.getUserType().toString());
//            principal.setEmailVerified(Boolean.TRUE.equals(user.getEmailVerified()));
//            principal.setLastLogin(user.getLastLogin());
//            principal.setCreatedAt(user.getCreatedAt());
//
//            // Build Authentication with your UserDto as principal
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            principal,
//                            null,
//                            Collections.emptyList() // add roles if you need authorities
//                    );
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//            // Put into SecurityContext so @AuthenticationPrincipal works
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        } catch (Exception ex) {
//            // On any parsing/validation error, don't authenticate; just continue
//        }
//
//        chain.doFilter(request, response);
//    }
//}


package com.sportyfi.config;

import com.sportyfi.services.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        UUID userId = jwtService.extractUserId(jwt);

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId.toString());
            if (jwtService.isTokenValid(jwt)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
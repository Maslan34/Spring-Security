package com.MuharremAslan.Security;


import com.MuharremAslan.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService UserDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl UserDetailsService) {
        this.jwtService = jwtService;
        this.UserDetailsService = UserDetailsService;
    }

    // Validating every incoming request here.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            username = jwtService.extractUserDetails(jwtToken);
        }
        //Each request is a thread and there will be no "Auth" in this thread, we check this here
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails user = UserDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(jwtToken, user)) {
                // We create an authentication object based on the existing information and throw it into the security context.
                // It won't go through these guys again anymore.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response); // continue with other chains
    }
}

package org.example.ridesmart.SecurityConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ridesmart.Service.JWT.JwtService;
import org.example.ridesmart.Service.JWT.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;
@Component
public class JwtNewFilter extends OncePerRequestFilter {

    private static final Logger log = Logger.getLogger(JwtNewFilter.class.getName());

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailService userDetailService;
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/User/Login") || path.equals("/User/SignUp") || path.equals("/User/profile");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
            try {
                email = jwtService.extractEmail(token);
            } catch (Exception e) {
                log.warning("Failed to extract email from token: " + e.getMessage());
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("Processing authentication for email: " + email);

            UserDetails userDetails = userDetailService.loadUserByUsername(email);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Authentication successful for user: " + email);
            } else {
                log.warning("JWT token validation failed for user: " + email);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                return; // End filter chain if token is invalid
            }
        }

        filterChain.doFilter(request, response);
    }
}

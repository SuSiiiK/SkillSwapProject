package com.example.backend.SkillSwap.security.filter;

import com.example.backend.SkillSwap.utils.JwtUtil;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            try {
                if (isValidJwt(token)) {
                    String username = jwtUtil.extractUsername(token);
                    System.out.println("Extracted username: " + username); // Логируем имя пользователя

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        if (jwtUtil.validateToken(token, username)) {
                            SecurityContextHolder.getContext().setAuthentication(
                                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>())
                            );
                            System.out.println("Authentication set for user: " + username); // Логируем успешную аутентификацию
                        }
                    }
                } else {
                    System.out.println("Invalid JWT format: " + token);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT format.");
                    return;
                }
            } catch (MalformedJwtException e) {
                System.out.println("Malformed JWT token: " + token);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Malformed JWT token.");
                return;
            }
        } else {
            System.out.println("No valid Authorization header found.");
        }

        filterChain.doFilter(request, response);
    }

    // Метод для проверки валидности токена (наличие двух точек)
    private boolean isValidJwt(String token) {
        return token != null && token.split("\\.").length == 3;
    }
}

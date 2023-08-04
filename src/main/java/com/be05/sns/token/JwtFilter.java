package com.be05.sns.token;

import com.be05.sns.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils utils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.split(" ")[1];

            if (utils.validate(token)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                String userName = utils.parseClaims(token).getSubject();

                AbstractAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                UserDto.builder()
                                        .userName(userName)
                                        .build(), token, new ArrayList<>());

                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}

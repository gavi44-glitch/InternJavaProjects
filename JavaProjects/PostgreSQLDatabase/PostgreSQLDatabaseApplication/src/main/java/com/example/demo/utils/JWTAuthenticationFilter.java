package com.example.demo.utils;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JWTService jwtService;


    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;

        String userID = null;
        String path = request.getServletPath();

        try {

            // untuk skip swagger endpoint
            if(path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                    path.startsWith("/swagger-resources")
            )
            {
                filterChain.doFilter(request, response);
                return;
            }
            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                token = authHeader.substring(7);


                userID = jwtService.extractUserID(token);
            }

            if (userID != null && SecurityContextHolder.getContext().getAuthentication() == null) {


                User user = userService.findUser(userID);
                String foundUserID = user.getUserID();


                if (jwtService.validateToken(token, foundUserID)) {

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    // bisa disebut sebagai principal, dan diakses dgn auth.getPrincipal
                                    foundUserID,
                                    null,
                                    Collections.emptyList()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);

    }
}

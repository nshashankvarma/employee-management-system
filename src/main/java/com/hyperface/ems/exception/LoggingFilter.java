package com.hyperface.ems.exception;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Log incoming requests
        logger.info("Request received - Method: {}, URI: {}", request.getMethod(), request.getRequestURI());
        System.out.println("Logged!!!!!!!!!");
        filterChain.doFilter(request, response);

        // Log response status
        logger.info("Response status: {}", response.getStatus());
    }
}

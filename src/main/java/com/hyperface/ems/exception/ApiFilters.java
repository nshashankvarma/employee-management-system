package com.hyperface.ems.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@Order(1)
public class ApiFilters implements Filter {
    @Value("${api.key.user}")
    String userApiKey;

    @Value("${api.key.admin}")
    String adminApiKey;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ((HttpServletResponse) response).setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String apiKey = req.getHeader("X-API-KEY");
        System.out.println(userApiKey + " " + apiKey);
        String url = ((HttpServletRequest) request).getRequestURI();
        if((url.contains("/assignDept") || url.contains("/assignProj") || url.contains("/delete"))){
            if(Objects.equals(adminApiKey, apiKey)){
                chain.doFilter(request, response);
            }
            else{
                response.getOutputStream().write(objectMapper.writeValueAsString(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, "Wrong Credentials", "Unauthorized Acccess!")).getBytes());;
            }
        }
        else if(Objects.equals(apiKey, userApiKey))
            chain.doFilter(request, response);
        else{
            response.getOutputStream().write(objectMapper.writeValueAsString(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, "Wrong Credentials", "Invalid API Key!")).getBytes());;

        }
    }
}
package com.josko.passenger.security.jwt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HttpMessageConverter<String> messageConverter = new StringHttpMessageConverter();


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
            throws IOException {
        ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        outputMessage.setStatusCode(HttpStatus.UNAUTHORIZED);

        messageConverter.write("UNAUTHORIZED", MediaType.APPLICATION_JSON, outputMessage);   
    }
}

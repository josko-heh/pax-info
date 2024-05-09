package com.josko.passenger.presentation.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.josko.passenger.config.Definitions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static com.josko.passenger.config.Definitions.RES;


@ControllerAdvice
public class ResponseBodyAdviceLogger implements ResponseBodyAdvice<Object> {

    private final Logger log = LogManager.getLogger(Definitions.DATA_LOGGER);
    
    private static final String LOG_PATTERN = "[S{}] {}";
    private static final String EMPTY = "{}";
    
    private static final ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);  

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        HttpServletResponse httpServletResponse = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();

        if(!httpServletRequest.getRequestURL().toString().contains("actuator")) {
            final var status = httpServletResponse.getStatus();
            
            String body;
            if(o == null) {
                body = EMPTY;
            } else {
                try {
                    body = mapper.writeValueAsString(o);
                } catch (JsonProcessingException ex) {
                    body = o.toString();
                }
            }

            log.warn(RES, LOG_PATTERN, status, body);
        }

        return o;
    }
}

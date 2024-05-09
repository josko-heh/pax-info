package com.josko.passenger.presentation.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.josko.passenger.config.Definitions;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.josko.passenger.config.Definitions.REQ;


@ControllerAdvice
public class RequestBodyAdviceLogger extends RequestBodyAdviceAdapter {

    private final Logger log = LogManager.getLogger(Definitions.DATA_LOGGER);

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {

        if (!httpServletRequest.getRequestURL().toString().contains("actuator")) {
            final Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                headers.put(key, httpServletRequest.getHeader(key));
            }

            final StringBuilder sb = new StringBuilder()
                    .append(httpServletRequest.getMethod()).append(" ")
                    .append(httpServletRequest.getRequestURL());

            headers.forEach((k, v) -> sb.append(" -H \"").append(k).append(": ").append(v).append("\""));

            sb.append(" -d '");
            try {
                sb.append(mapper.writeValueAsString(body));
            } catch (JsonProcessingException ex) {
                sb.append(body);
            }
            sb.append("'");

            log.info(REQ, sb.toString());
        }

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}

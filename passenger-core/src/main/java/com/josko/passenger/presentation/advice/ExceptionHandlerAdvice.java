package com.josko.passenger.presentation.advice;

import com.josko.passenger.config.Definitions;
import com.josko.passenger.exceptions.PassengerModuleException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Component
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private final Logger errorLog = LogManager.getLogger(Definitions.ERROR_LOGGER);
    
    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(final RuntimeException exception) {
        errorLog.error(exception);
    }

    @ResponseBody
    @ExceptionHandler({PassengerModuleException.class})
    public ResponseEntity<String> handleException(final PassengerModuleException exception) {
        errorLog.error(exception);
        return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
    }
}

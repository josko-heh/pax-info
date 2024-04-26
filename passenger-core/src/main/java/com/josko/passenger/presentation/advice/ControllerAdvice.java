package com.josko.passenger.presentation.advice;

import com.josko.passenger.exceptions.PassengerModuleException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Component
@RestControllerAdvice
@Log4j2
public class ControllerAdvice {

    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(final RuntimeException exception) {
        log.error(exception);
        exception.printStackTrace();
    }

    @ResponseBody
    @ExceptionHandler({PassengerModuleException.class})
    public ResponseEntity<String> handleException(final PassengerModuleException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
    }
}

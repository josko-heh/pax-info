package com.josko.passenger.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class PassengerModuleException extends RuntimeException {

    private final String message;

    private final HttpStatus httpStatus;
}

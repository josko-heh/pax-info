package com.josko.passenger.presentation.dto.keys;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDate;

@Getter
@Setter
public class TicketNumberKeyDTO extends KeyDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    private String carrier;
    private String flightNumber;
    private LocalDate departureDate;
    private String ticketNumber;

    @Override
    public String toString() {
        return String.format("{<%s>@%s%s#%s}", ticketNumber, carrier, flightNumber, departureDate);
    }    
}

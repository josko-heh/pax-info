package com.josko.passenger.update.dto.keys;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
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

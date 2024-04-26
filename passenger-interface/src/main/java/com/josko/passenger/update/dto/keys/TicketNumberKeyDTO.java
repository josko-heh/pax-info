package com.josko.passenger.update.dto.keys;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonTypeName("TICKET_NUMBER")
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

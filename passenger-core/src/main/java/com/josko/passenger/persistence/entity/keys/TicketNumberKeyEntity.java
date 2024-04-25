package com.josko.passenger.persistence.entity.keys;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static com.josko.passenger.persistence.entity.keys.Key.Type.TKNE;

@Entity
@IdClass(TicketNumberKeyEntity.CompositeId.class)
@Table(name = "pax_data_passenger_key_tkne")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class TicketNumberKeyEntity extends KeyEntity {

    @Data
    public static class CompositeId {
        private String carrier;
        private String flightNumber;
        private LocalDate departureDate;
        private String ticketNumber;
    }

    @Id
    @Column(name = "carrier")
    private String carrier;
    @Id
    @Column(name = "flight_number")
    private String flightNumber;
    @Id
    @Column(name = "scheduled_departure")
    private LocalDate departureDate;
    @Id
    @Column(name = "ticket_number")
    private String ticketNumber;

    @Override
    public Type getType() {
        return TKNE;
    }
    
    @Override
    public String toString() {
        return String.format("{<%s>@%s%s#%s}", ticketNumber, carrier, flightNumber, departureDate);
    }      
}

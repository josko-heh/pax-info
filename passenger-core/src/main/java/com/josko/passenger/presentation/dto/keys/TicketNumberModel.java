package com.josko.passenger.presentation.dto.keys;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@JsonTypeName("TICKET_NUMBER")
@EqualsAndHashCode(callSuper = false)
public class TicketNumberModel extends KeyModel {

    private String ticketNumber;
    
    @Override
    public String toString() {
        return "{<" + ticketNumber + ">}";
    }    
}
package com.josko.passenger.presentation.dto.slices;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.io.Serializable;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SliceDTO implements Serializable {

    public enum Type {
        PASSENGER_DETAILS, BOOKING
    }

    private Type type;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type",
            visible = true)
    @JsonSubTypes({
            @JsonSubTypes.Type(name = "PASSENGER_DETAILS", value = PassengerDetailsDTO.class),
            @JsonSubTypes.Type(name = "BOOKING", value = BookingDTO.class),
    })
    private SliceDataDTO data;

}

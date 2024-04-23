package com.josko.passenger.presentation.dto.slices;

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
        PASSENGER_DETAILS
    }

    private Type dataSlice;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "data_slice",
            visible = true)
/*    @JsonSubTypes({
            @JsonSubTypes.Type(name = "PASSENGER_DETAILS", value = PassengerDetails.class),
    })*/
    private SliceDataDTO data;

}

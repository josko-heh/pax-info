package com.josko.passenger.presentation.dto.keys;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "TICKET_NUMBER", value = TicketNumberKeyDTO.class),
//        @JsonSubTypes.Type(name = "PNR_LOCATOR_AND_NAME", value = PnrLocatorAndNameModel.class),
})
public abstract class KeyDTO implements Serializable {

    private Type type;

    public enum Type {
        TICKET_NUMBER
    }

}
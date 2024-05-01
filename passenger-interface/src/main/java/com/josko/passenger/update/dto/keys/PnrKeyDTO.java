package com.josko.passenger.update.dto.keys;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonTypeName("PNR")
public class PnrKeyDTO extends KeyDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    private String locator;
    private String firstName;
    private String lastName;
    
    @Override
    public String toString() {
        return "{<" + firstName + " " + lastName + ">@" + locator+ '}';
    }
}

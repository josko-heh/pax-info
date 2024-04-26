package com.josko.passenger.update.slices;

import lombok.*;

import java.io.Serial;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"firstName", "lastName", "dateOfBirth"})
public class PassengerDetailsData implements SliceData {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private String firstName;
    private String lastName;
    private String title;
    private String gender;
    private LocalDate dateOfBirth;
    private String city;
    private String zipCode;
    private String streetAndNumber;
    private String county;
    private String language;

    @Override
    public boolean isEmpty() {
        return firstName == null && lastName == null && dateOfBirth == null;
    }
}

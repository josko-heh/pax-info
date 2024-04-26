package com.josko.passenger.presentation.dto.slices;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"firstName", "lastName", "dateOfBirth"})
@ToString(of = {"firstName", "lastName"})
@JsonTypeName("PASSENGER_DETAILS")
public class PassengerDetailsDTO implements SliceDataDTO {

    private String firstName;
    private String lastName;
    private String title;
    private String gender;
    private LocalDate dateOfBirth;
    private String city;
    private String zipCode;
    private String streetAndNumber;
    private String country;
    private String language;

    @Override
    public boolean isSliceDataEmpty() {
        return firstName == null && lastName == null && dateOfBirth == null;
    }
}
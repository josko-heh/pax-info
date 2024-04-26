package com.josko.passenger.persistence.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pax_data_passenger_details")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {"firstName", "lastName", "dateOfBirth"})
public class PassengerDetailsEntity extends SliceMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "details_id")
    private UUID detailsId;

    @OneToOne
    @JoinColumn(name = "passenger_id")
    private PassengerEntity passenger;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "street_and_number")
    private String streetAndNumber;

    @Column(name = "country")
    private String country;

    @Column(name = "language")
    private String language;
}

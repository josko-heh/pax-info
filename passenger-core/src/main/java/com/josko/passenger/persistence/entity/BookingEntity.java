package com.josko.passenger.persistence.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "pax_data_booking")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"locator", "tattoo"})
public class BookingEntity extends SliceMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "booking_id")
    private UUID bookingID;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private PassengerEntity passenger;

    @Column(name = "locator")
    private String locator;

    @Column(name = "tattoo")
    private Integer tattoo;

    @Column(name = "point_of_sale")
    private String pointOfSale;
}

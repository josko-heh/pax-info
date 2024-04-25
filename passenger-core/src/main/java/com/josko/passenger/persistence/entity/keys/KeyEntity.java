package com.josko.passenger.persistence.entity.keys;

import com.josko.passenger.persistence.entity.PassengerEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class KeyEntity implements Key {

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private PassengerEntity passenger;
}

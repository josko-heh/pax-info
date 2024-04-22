package com.josko.passenger.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "pax_data_passenger")
@Getter
@Setter
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "passenger_id")
    private UUID passengerId;

    @Column(name = "created_ts")
    private Instant createdTs;

    @Column(name = "updated_ts")
    private Instant updatedTs;

    @Column(name = "purge_ts")
    private Instant purgeTs;

    @PrePersist
    public void onPrePersist() {
        this.createdTs = Instant.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedTs = Instant.now();
    }
}

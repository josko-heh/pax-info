package com.josko.passenger.persistence.repository;

import com.josko.passenger.persistence.entity.PassengerDetailsEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PassengerDetailsRepository extends JpaRepository<PassengerDetailsEntity, UUID> {

    @Query("SELECT d FROM PassengerDetailsEntity d WHERE d.passenger.passengerId = :passengerID")
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<PassengerDetailsEntity> findByPassengerID(@Param("passengerID") UUID passengerID);

    void deleteByPassengerPassengerId(UUID passengerID);
}

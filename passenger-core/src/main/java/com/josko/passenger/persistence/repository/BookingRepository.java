package com.josko.passenger.persistence.repository;

import com.josko.passenger.persistence.entity.BookingEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    @Query("SELECT bkg FROM BookingEntity bkg WHERE bkg.passenger.passengerId = :passengerID")
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<BookingEntity> findByPassengerID(@Param("passengerID") UUID passengerID);

    void deleteByPassengerPassengerId(UUID passengerID);
}

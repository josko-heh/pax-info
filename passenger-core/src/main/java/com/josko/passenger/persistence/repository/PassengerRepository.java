package com.josko.passenger.persistence.repository;

import com.josko.passenger.persistence.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

public interface PassengerRepository extends JpaRepository<PassengerEntity, UUID>,
        JpaSpecificationExecutor<PassengerEntity>,
        KeySupport {

    @Modifying
    @Transactional
    @Query("DELETE FROM PassengerEntity p WHERE p.purgeTs < ?1")
    void purgePassengers(Instant purgeTs);

    @Modifying
    @Transactional
    @Query("UPDATE PassengerEntity p SET p.purgeTs = ?1 WHERE p.passengerId = ?2")
    void updatePassengerPurgeTs(Instant purgeTs, UUID passengerId);
}

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
    @Query(value = "DELETE FROM pax_data_passenger WHERE purge_ts < ?1 LIMIT ?2", nativeQuery = true)
    void purgePassengers(final Instant purgeTs, final Integer batchSize);

    @Modifying
    @Transactional
    @Query("UPDATE PassengerEntity p SET p.purgeTs = ?1 WHERE p.passengerId = ?2")
    void updatePassengerPurgeTs(final Instant purgeTs, final UUID passengerId);
}

package com.josko.passenger.persistence.repository;

import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.entity.keys.KeyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KeySupport {
    
    Optional<PassengerEntity> findByKey(KeyEntity key, Class<? extends KeyEntity> type);
    
    Optional<UUID> findPassengerIdByKey(KeyEntity key, Class<? extends KeyEntity> type);
    
    List<KeyEntity> findKeys(PassengerEntity entity);
    
    List<KeyEntity> findKeys(UUID passengerId);

    void saveKey(KeyEntity key);
}

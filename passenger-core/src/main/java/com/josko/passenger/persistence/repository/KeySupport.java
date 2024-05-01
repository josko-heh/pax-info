package com.josko.passenger.persistence.repository;

import com.josko.passenger.persistence.entity.keys.KeyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KeySupport {
    
    Optional<UUID> findPassengerIdByKey(KeyEntity key);
    
    List<KeyEntity> findKeys(UUID passengerId);

    void saveKey(KeyEntity key);
}

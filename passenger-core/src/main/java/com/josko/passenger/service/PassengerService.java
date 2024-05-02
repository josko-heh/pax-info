package com.josko.passenger.service;


import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PassengerService {
    
    Optional<PassengerEntity> getPassenger(UUID passengerId);

    Optional<UUID> getPassengerID(Collection<KeyEntity> keys);

    Set<KeyEntity> getPassengerKeys(UUID passengerId);

    PassengerEntity createPassenger(Set<KeyEntity> keys);
    
    void saveKeys(UUID passengerId, Set<KeyEntity> keys);

    Set<Slice> retrieveSlices(UUID passengerId, Collection<SliceData.Type> requestedSlices);
    
    void setPurgeTs(UUID passengerId);
}

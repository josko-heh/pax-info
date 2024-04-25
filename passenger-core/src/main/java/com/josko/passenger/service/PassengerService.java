package com.josko.passenger.service;


import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;

import java.util.*;

public interface PassengerService {
    
    Optional<PassengerEntity> getPassenger(UUID passengerId);

    Optional<PassengerEntity> getPassenger(Collection<KeyEntity> keys);
    
    Optional<UUID> getPassengerID(Collection<KeyEntity> keys);

    List<KeyEntity> getPassengerKeys(PassengerEntity passenger);
    
    List<KeyEntity> getPassengerKeys(UUID passengerId);

    PassengerEntity createPassenger(Set<KeyEntity> keys);
    
    void saveKeys(UUID passengerId, Set<KeyEntity> keys);

    Set<Slice> retrieveSlices(UUID passengerId, Collection<SliceData.Type> requestedTypes);

    void setPaxPurgeDate(UUID passengerId);
}

package com.josko.passenger.service.impl;

import com.google.common.collect.Sets;
import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.persistence.repository.PassengerRepository;
import com.josko.passenger.service.PassengerService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.josko.passenger.config.Definitions.PAX_ID_MDC;

@Service
@Log4j2
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository repository;

    
    @Autowired
    public PassengerServiceImpl(PassengerRepository repository) {
		this.repository = repository;
	}
    
    @Override
    public Optional<PassengerEntity> getPassenger(UUID passengerId) {
        return repository.findById(passengerId);
    }

    @Override
    public Optional<PassengerEntity> getPassenger(Collection<KeyEntity> keys) {
        return keys.stream()
                .flatMap(key -> repository.findByKey(key, key.getClass()).stream())
                .findFirst();
    }

    @Override
    public Optional<UUID> getPassengerID(Collection<KeyEntity> keys) {
		Optional<UUID> id = keys.stream()
                .flatMap(key -> repository.findPassengerIdByKey(key, key.getClass()).stream())
                .findFirst();
        
        id.ifPresent(it -> {
            try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.put(PAX_ID_MDC, it.toString())) {
                log.info( "Found existing passenger for keys {}", keys);
            }
        });
        
        return id;
    }
    
    @Override
    public List<KeyEntity> getPassengerKeys(PassengerEntity passenger) {
        return getPassengerKeys(passenger.getPassengerId());
    }
    
    @Override
    public List<KeyEntity> getPassengerKeys(UUID passengerId) {
        return repository.findKeys(passengerId);
    }

    @Override
    @Transactional
    public PassengerEntity createPassenger(Set<KeyEntity> keys) {
        final var passenger = new PassengerEntity();
        passenger.setPurgeTs(Instant.now().plus(10, ChronoUnit.DAYS));
        repository.save(passenger);
        
        try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.put(PAX_ID_MDC, passenger.getPassengerId().toString())) {
            log.info( "New passenger created with keys {}", keys);
            saveKeys(passenger, keys);
        }
        
        return passenger;
    }
    
    @Override
    @Transactional
    public void saveKeys(UUID passengerId, Set<KeyEntity> keys) {
        var newKeys = getNewKeys(passengerId, keys);
        
        if(!newKeys.isEmpty()) {
            final var passenger = getPassenger(passengerId).orElseThrow();
            saveKeys(passenger, newKeys);
        }
    }

    private void saveKeys(PassengerEntity passenger, Set<KeyEntity> keys) {
        keys.forEach(key -> {
            key.setPassenger(passenger);
            repository.saveKey(key);
        });
    }

    private Set<KeyEntity> getNewKeys(UUID passengerID, Set<KeyEntity> keys) {
        final var existingKeys = new HashSet<>(repository.findKeys(passengerID));

        return Sets.difference(keys, existingKeys);
    }
}

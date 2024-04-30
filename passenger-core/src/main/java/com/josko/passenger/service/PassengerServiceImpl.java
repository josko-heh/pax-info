package com.josko.passenger.service;

import com.google.common.collect.Sets;
import com.josko.passenger.exceptions.PassengerModuleException;
import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.persistence.repository.PassengerRepository;
import com.josko.passenger.service.provider.SliceProvider;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

import static com.josko.passenger.config.Definitions.PAX_ID_MDC;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Service
@Log4j2
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository repository;
    private final Map<SliceData.Type, SliceProvider<?>> providers;

    
    @Autowired
    public PassengerServiceImpl(PassengerRepository repository,
                                List<SliceProvider<?>> providers) {
		this.repository = repository;
        this.providers = providers.stream()
                .collect(toMap(SliceProvider::accepts, Function.identity()));
	}
    
    @Override
    public Optional<PassengerEntity> getPassenger(UUID passengerId) {
        return repository.findById(passengerId);
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
    public Set<KeyEntity> getPassengerKeys(UUID passengerId) {
        return new HashSet<>(repository.findKeys(passengerId));
    }

    @Override
    public Set<Slice> retrieveSlices(UUID passengerId, Collection<SliceData.Type> requestedSlices) {
        return requestedSlices.stream()
                .map(slice -> retrieveSlice(passengerId, slice))
                .collect(toSet());
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

    private Slice retrieveSlice(UUID passenger, SliceData.Type requestedSlice) {

        final SliceProvider provider = providers.get(requestedSlice);

        if (provider == null) {
            throw new PassengerModuleException("No provider for slice " + requestedSlice.name(), HttpStatus.NOT_IMPLEMENTED);
        }

        return provider.provide(passenger);
    }
}

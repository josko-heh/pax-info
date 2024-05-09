package com.josko.passenger.service.provider;

import com.josko.passenger.config.Definitions;
import com.josko.passenger.persistence.entity.PassengerDetailsEntity;
import com.josko.passenger.persistence.repository.PassengerDetailsRepository;
import com.josko.passenger.service.mappers.PassengerDetailsMapper;
import com.josko.passenger.update.slices.PassengerDetailsData;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.josko.passenger.update.slices.SliceData.Type.DETAILS;


@Service
@RequiredArgsConstructor
public class PassengerDetailsProvider implements SliceProvider<PassengerDetailsData> {

    private final Logger debugLog = LogManager.getLogger(Definitions.DEBUG_LOGGER);
    
    private final PassengerDetailsRepository repository;
    private final PassengerDetailsMapper mapper;

    @Override
    public SliceData.Type accepts() {
        return DETAILS;
    }
    
    @Override
    public Slice<PassengerDetailsData> provide(UUID passengerId) {
        debugLog.debug("Populating {} data slice.", accepts());

        final var detailEntity = repository.findByPassengerID(passengerId).orElse(new PassengerDetailsEntity());
        
        return Slice.<PassengerDetailsData>builder()
                .content(mapper.toDto(detailEntity))
                .name(accepts())
                .build();
    }
}

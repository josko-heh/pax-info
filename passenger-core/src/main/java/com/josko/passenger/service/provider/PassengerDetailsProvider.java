package com.josko.passenger.service.provider;

import com.josko.passenger.persistence.entity.PassengerDetailsEntity;
import com.josko.passenger.persistence.repository.PassengerDetailsRepository;
import com.josko.passenger.service.mappers.PassengerDetailsMapper;
import com.josko.passenger.update.slices.PassengerDetailsData;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.josko.passenger.update.slices.SliceData.Type.DETAILS;


@Service
@RequiredArgsConstructor
@Log4j2
public class PassengerDetailsProvider implements SliceProvider<PassengerDetailsData> {

    private final PassengerDetailsRepository repository;
    private final PassengerDetailsMapper mapper;

    @Override
    public SliceData.Type accepts() {
        return DETAILS;
    }
    
    @Override
    public Slice<PassengerDetailsData> provide(UUID passengerId) {
		log.debug("Populating {} data slice.", accepts());

        final var detailEntity = repository.findByPassengerID(passengerId).orElse(new PassengerDetailsEntity());
        
        return Slice.<PassengerDetailsData>builder()
                .content(mapper.toDto(detailEntity))
                .name(accepts())
                .build();
    }
}

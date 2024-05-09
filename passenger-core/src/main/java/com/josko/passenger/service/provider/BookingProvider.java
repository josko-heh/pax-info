package com.josko.passenger.service.provider;

import com.josko.passenger.config.Definitions;
import com.josko.passenger.persistence.entity.BookingEntity;
import com.josko.passenger.persistence.repository.BookingRepository;
import com.josko.passenger.service.mappers.BookingMapper;
import com.josko.passenger.update.slices.BookingData;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.josko.passenger.update.slices.SliceData.Type.BOOKING;


@Service
@RequiredArgsConstructor
public class BookingProvider implements SliceProvider<BookingData> {

    private final Logger debugLog = LogManager.getLogger(Definitions.DEBUG_LOGGER);

    private final BookingRepository repository;
    private final BookingMapper mapper;

    @Override
    public SliceData.Type accepts() {
        return BOOKING;
    }
    
    @Override
    public Slice<BookingData> provide(UUID passengerId) {
        debugLog.debug("Populating {} data slice.", accepts());

        final var bookingEntity = repository.findByPassengerID(passengerId).orElse(new BookingEntity());

        return Slice.<BookingData>builder()
                    .content(mapper.toDto(bookingEntity))
                    .name(accepts())
                    .build();
    }
}

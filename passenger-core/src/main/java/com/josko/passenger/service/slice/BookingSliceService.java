package com.josko.passenger.service.slice;

import com.josko.passenger.persistence.entity.BookingEntity;
import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.repository.BookingRepository;
import com.josko.passenger.persistence.repository.PassengerRepository;
import com.josko.passenger.service.mappers.BookingMapper;
import com.josko.passenger.update.dto.UpdateMetaData;
import com.josko.passenger.update.slices.BookingData;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.josko.passenger.update.slices.SliceData.Type.BOOKING;

@Service
@RequiredArgsConstructor
public class BookingSliceService implements SliceService<BookingData> {

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;


    @Override
    public SliceData.Type accepts() {
        return BOOKING;
    }
    
    @Override
    @Transactional
    public void persistSlice(final UUID passengerId, UpdateMetaData metaData, final Slice<BookingData> slice) {
        bookingRepository.findByPassengerID(passengerId).ifPresentOrElse(
                data -> updateData(data, metaData, slice.getContent()),
                () -> saveData(passengerId, metaData, slice.getContent())
        );
    }

    @Override
    @Transactional
    public void deleteSlice(PassengerEntity passenger) {
        passenger.setPassengerDetails(null);
        bookingRepository.deleteByPassengerPassengerId(passenger.getPassengerId());
    }

    private void saveData(final UUID passengerId, UpdateMetaData metaData, final BookingData data) {
        final var passenger = passengerRepository.findById(passengerId).orElseThrow();
        final var details = bookingMapper.fromDto(passenger, data, metaData);
        bookingRepository.save(details);
    }

    private void updateData(final BookingEntity entity, UpdateMetaData metaData, final BookingData data) {
        bookingMapper.update(entity, data, metaData);
        bookingRepository.save(entity);
    }

}

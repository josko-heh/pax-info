package com.josko.passenger.service.slice;

import com.josko.passenger.persistence.entity.PassengerDetailsEntity;
import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.repository.PassengerDetailsRepository;
import com.josko.passenger.persistence.repository.PassengerRepository;
import com.josko.passenger.service.mappers.PassengerDetailsMapper;
import com.josko.passenger.update.dto.UpdateMetaData;
import com.josko.passenger.update.slices.PassengerDetailsData;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.josko.passenger.update.slices.SliceData.Type.DETAILS;

@Service
@RequiredArgsConstructor
public class DetailsSliceService implements SliceService<PassengerDetailsData> {

    private final PassengerRepository passengerRepository;
    private final PassengerDetailsRepository detailsRepository;
    private final PassengerDetailsMapper detailsMapper;


    @Override
    public SliceData.Type accepts() {
        return DETAILS;
    }
    
    @Override
    @Transactional
    public void persistSlice(final UUID passengerId, UpdateMetaData metaData, final Slice<PassengerDetailsData> slice) {
        detailsRepository.findByPassengerID(passengerId).ifPresentOrElse(
                data -> updateDetailsData(data, metaData, slice.getContent()),
                () -> saveDetailsData(passengerId, metaData, slice.getContent())
        );
    }

    @Override
    @Transactional
    public void deleteSlice(PassengerEntity passenger) {
        passenger.setPassengerDetails(null);
        detailsRepository.deleteByPassengerPassengerId(passenger.getPassengerId());
    }

    private void saveDetailsData(final UUID passengerId, UpdateMetaData metaData, final PassengerDetailsData data) {
        final var passenger = passengerRepository.findById(passengerId).orElseThrow();
        final var details = detailsMapper.fromDto(passenger, data, metaData);
        detailsRepository.save(details);
    }

    private void updateDetailsData(final PassengerDetailsEntity entity, UpdateMetaData metaData, final PassengerDetailsData data) {
        detailsMapper.update(entity, data, metaData);
        detailsRepository.save(entity);
    }

}

package com.josko.passenger.service.provider;

import com.josko.passenger.exceptions.PassengerModuleException;
import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.service.PassengerService;
import com.josko.passenger.service.mappers.KeyMapper;
import com.josko.passenger.service.mappers.SliceMapper;
import com.josko.passenger.update.dto.keys.KeyDTO;
import com.josko.passenger.presentation.dto.slices.SliceDTO;
import com.josko.passenger.presentation.dto.slices.PassengerData;
import com.josko.passenger.update.slices.Slice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.josko.passenger.config.Definitions.PAX_ID_MDC;
import static org.apache.logging.log4j.CloseableThreadContext.put;

@Service
@RequiredArgsConstructor
@Log4j2
public class PassengerDataProviderImpl implements PassengerDataProvider {

    private final PassengerService paxService;
    private final SliceMapper sliceMapper;
    private final KeyMapper keyMapper;

    @Override
    public PassengerData retrieve(final List<SliceDTO.Type> dataSliceTypes, final KeyDTO query) {
        final UUID passengerID = paxService.getPassengerID(Collections.singletonList(keyMapper.toEntity(query)))
                .orElseThrow(() -> new PassengerModuleException("No passenger entity found.", HttpStatus.NOT_FOUND));
        
        try (final Instance ctc = put(PAX_ID_MDC, passengerID.toString())) {
            log.debug("Passenger found!");

            Set<Slice> slices = paxService.retrieveSlices(passengerID, sliceMapper.map(dataSliceTypes));

            Set<KeyEntity> keys = paxService.getPassengerKeys(passengerID);

            if (slices.stream().allMatch(slice -> slice.getContent().isEmpty())) {
                throw new PassengerModuleException("No data!", HttpStatus.NO_CONTENT);
            }
            
            return PassengerData.builder()
                    .passengerId(passengerID)
                    .dataSlices(sliceMapper.toDTOs(slices))
                    .keys(keyMapper.toDTOs(keys))
                    .build();
        }
    }
}
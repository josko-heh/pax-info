package com.josko.passenger.service.impl;

import com.josko.passenger.presentation.dto.keys.KeyModel;
import com.josko.passenger.presentation.dto.slices.SliceDTO;
import com.josko.passenger.presentation.dto.slices.PassengerData;
import com.josko.passenger.service.PassengerDataProvider;
import com.josko.passenger.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.josko.passenger.config.Definitions.PAX_ID_MDC;
import static org.apache.logging.log4j.CloseableThreadContext.put;

@Service
@RequiredArgsConstructor
@Log4j2
public class PassengerDataProviderImpl implements PassengerDataProvider {

//    private final PassengerService paxService;

   /* private final PassengerInformationMapper paxMapper;

    private final KeyMapper keyMapper;*/

    @Override
    public PassengerData retrieve(final List<SliceDTO.Type> dataSliceTypes, final KeyModel query) {
        final UUID passengerID = UUID.randomUUID();/*
        final UUID passengerID = paxService.retrievePassengerID(Collections.singletonList(keyMapper.toEntity(query)))
                .orElseThrow(() -> new PassengerModuleException("No passenger entity found.", HttpStatus.NOT_FOUND));
*/
        try (final Instance ctc = put(PAX_ID_MDC, passengerID.toString())) {

            log.debug("Passenger found!");

/*            Set<Slice> slices = paxService.retrieveSlices(passengerID);

            List<KeyEntity> keys  = paxService.getPassengerKeys(passengerID);

            if (slices.stream().allMatch(slice -> slice.getContent().isDataSliceEmpty())) {
                throw new PassengerModuleException("Not found!", HttpStatus.NO_CONTENT);
            }*/
/*
            return PassengerData.builder()
                    .passengerId(passengerID)
                    .dataSlices(paxMapper.toDataSliceDto(slices))
                    .keys(Set.of(keys))
                    .build();*/
            return PassengerData.builder()
                    .passengerId(passengerID)
                    .build();
        }
    }
}
package com.josko.passenger.service.impl;

import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.presentation.dto.requests.UpdateMetaData;
import com.josko.passenger.service.SliceHandler;
import com.josko.passenger.service.slices.PassengerDetailsData;
import com.josko.passenger.service.slices.Slice;
import com.josko.passenger.service.slices.SliceData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DetailsSliceHandler implements SliceHandler<PassengerDetailsData> {

    private final SliceService<PassengerDetailsData> sliceService;

    @Override
    public SliceData.Type accepts() {
        return SliceData.Type.DETAILS;
    }

    @Override
    public void handle(UUID passengerId, UpdateMetaData metaData, Slice<PassengerDetailsData> slice) {
        sliceService.persistSlice(passengerId, metaData, slice);
    }

    @Override
    public void deleteSlice(PassengerEntity passenger) {
        sliceService.deleteSlice(passenger);
    }
}

package com.josko.passenger.service;


import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.presentation.dto.requests.UpdateMetaData;
import com.josko.passenger.service.slices.Slice;
import com.josko.passenger.service.slices.SliceData;

import java.util.UUID;

public interface SliceHandler<T extends SliceData> {

    SliceData.Type accepts();

    void handle(UUID passengerId, UpdateMetaData metaData, Slice<T> slice);

    void deleteSlice(PassengerEntity passenger);
}

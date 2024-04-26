package com.josko.passenger.service.provider;

import com.josko.passenger.update.dto.keys.KeyDTO;
import com.josko.passenger.presentation.dto.slices.SliceDTO;
import com.josko.passenger.presentation.dto.slices.PassengerData;

import java.util.List;

public interface PassengerDataProvider {

    PassengerData retrieve(List<SliceDTO.Type> dataSlices, KeyDTO query);

}
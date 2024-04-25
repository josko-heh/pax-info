package com.josko.passenger.service;

import com.josko.passenger.presentation.dto.keys.KeyDTO;
import com.josko.passenger.presentation.dto.slices.SliceDTO;
import com.josko.passenger.presentation.dto.slices.PassengerData;

import java.util.List;

public interface PassengerDataProvider {

    PassengerData retrieve(List<SliceDTO.Type> dataSlices, KeyDTO query);

}
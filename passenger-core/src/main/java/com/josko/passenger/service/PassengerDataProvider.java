package com.josko.passenger.service;

import com.josko.passenger.presentation.dto.keys.KeyModel;
import com.josko.passenger.presentation.dto.slices.DataSliceModel;
import com.josko.passenger.presentation.dto.slices.PassengerData;

import java.util.List;

public interface PassengerDataProvider {

    PassengerData retrieve(List<DataSliceModel.Type> dataSlices, KeyModel query);

}
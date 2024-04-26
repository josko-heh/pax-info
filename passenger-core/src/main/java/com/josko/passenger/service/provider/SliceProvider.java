package com.josko.passenger.service.provider;


import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;

import java.util.UUID;

public interface SliceProvider<D extends SliceData> {
    
    SliceData.Type accepts();
    
    Slice<D> provide(UUID passengerID);
}

package com.josko.passenger.update.dto;

import com.josko.passenger.update.dto.keys.KeyDTO;
import com.josko.passenger.update.slices.Slice;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PassengerUpdate implements UpdateMetaData, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID reference;

    private String datasource;
    
    private List<KeyDTO> keys = new ArrayList<>();

    private List<Slice> slices = new ArrayList<>();

    private Instant updateTs;
}

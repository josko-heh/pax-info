package com.josko.passenger.presentation.dto.requests;


import com.josko.passenger.presentation.dto.keys.KeyDTO;
import com.josko.passenger.presentation.dto.slices.SliceDTO;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDataRequest implements Serializable {

    @Builder.Default
    private List<SliceDTO.Type> requestedTypes = new ArrayList<>();

    private KeyDTO key;
}

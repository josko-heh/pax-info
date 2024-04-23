package com.josko.passenger.presentation.dto.slices;

import com.josko.passenger.presentation.dto.keys.KeyModel;
import lombok.*;

import java.io.Serializable;
import java.util.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PassengerData implements Serializable {

    private UUID passengerId;

    @Builder.Default
    private List<SliceDTO> dataSlices = new ArrayList<>();

    @Builder.Default
    private Set<KeyModel> keys = new HashSet<>();
}
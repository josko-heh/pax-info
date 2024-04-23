package com.josko.passenger.service.slices;

import lombok.*;

import java.time.Instant;

@Builder
@Data
public class Slice<T extends SliceData> {

    private SliceData.Type name;

    private T content;

    private Instant updateTs;
}

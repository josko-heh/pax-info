package com.josko.passenger.service.slices;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Builder
@Data
public class Slice<T extends SliceData> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    private SliceData.Type name;

    private T content;

    private Instant updateTs;
}

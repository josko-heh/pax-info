package com.josko.passenger.service.slices;


import java.io.Serializable;

public interface SliceData extends Serializable {
    enum Type {
        DETAILS
    }

    boolean isEmpty();
}

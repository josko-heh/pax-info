package com.josko.passenger.update.slices;


import java.io.Serializable;

public interface SliceData extends Serializable {
    enum Type {
        DETAILS
    }

    boolean isEmpty();
}

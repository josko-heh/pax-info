package com.josko.passenger.persistence.entity.keys;


public interface Key {

    Type getType();

    enum Type {
        TKNE, PNR
    }
}

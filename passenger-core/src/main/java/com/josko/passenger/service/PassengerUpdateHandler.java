package com.josko.passenger.service;


import com.josko.passenger.update.dto.PassengerUpdate;

public interface PassengerUpdateHandler {

    void handle(PassengerUpdate event);
}

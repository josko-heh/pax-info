package com.josko.passenger.service.handler;


import com.josko.passenger.update.dto.PassengerUpdate;

public interface PassengerUpdateHandler {

    void handle(PassengerUpdate event);
}

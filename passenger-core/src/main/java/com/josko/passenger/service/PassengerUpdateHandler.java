package com.josko.passenger.service;


import com.josko.passenger.presentation.dto.requests.PassengerUpdate;

public interface PassengerUpdateHandler {

    void handle(PassengerUpdate event);
}

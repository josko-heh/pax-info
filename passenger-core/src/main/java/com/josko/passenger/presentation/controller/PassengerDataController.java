package com.josko.passenger.presentation.controller;

import com.josko.passenger.presentation.dto.requests.PassengerDataRequest;
import com.josko.passenger.presentation.dto.slices.PassengerData;
import com.josko.passenger.service.PassengerDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/passenger-data", produces = MediaType.APPLICATION_JSON_VALUE)
public class PassengerDataController {

	private final PassengerDataProvider dataProvider;

	@GetMapping
	public ResponseEntity<PassengerData> getPassengerData(@RequestBody PassengerDataRequest request) {
		var passengerData = dataProvider.retrieve(request.getSliceTypes(), request.getQuery());
		return ResponseEntity.ok(passengerData);
	}

}
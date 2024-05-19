package com.josko.passenger.presentation.controller;

import com.josko.passenger.presentation.dto.requests.PassengerDataRequest;
import com.josko.passenger.presentation.dto.slices.PassengerData;
import com.josko.passenger.service.provider.PassengerDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/passenger-data", produces = MediaType.APPLICATION_JSON_VALUE)
public class PassengerDataController {

	private final PassengerDataProvider dataProvider;

	@GetMapping
	@Secured("ROLE_USER")
	public ResponseEntity<PassengerData> getPassengerData(@RequestBody PassengerDataRequest request) {
		var passengerData = dataProvider.retrieve(request.getRequestedTypes(), request.getKey());
		return ResponseEntity.ok(passengerData);
	}

}

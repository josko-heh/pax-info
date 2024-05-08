package com.josko.passenger.presentation.controller;

import com.josko.passenger.presentation.dto.requests.PassengerDataRequest;
import com.josko.passenger.presentation.dto.slices.*;
import com.josko.passenger.service.provider.PassengerDataProvider;
import com.josko.passenger.update.dto.keys.KeyDTO;
import com.josko.passenger.update.dto.keys.PnrKeyDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PassengerDataControllerTest {

	@Mock
	private PassengerDataProvider dataProvider;

	@InjectMocks
	private PassengerDataController controller;

	@Test
	void testGetPassengerData() {
		PassengerDataRequest request = PassengerDataRequest.builder()
				.requestedTypes(Collections.singletonList(SliceDTO.Type.BOOKING))
				.key(PnrKeyDTO.builder()
						.type(KeyDTO.Type.PNR)
						.locator("XYZ123")
						.firstName("John")
						.lastName("Doe")
						.build())
				.build();

		PassengerData passengerData = PassengerData.builder()
				.passengerId(UUID.randomUUID())
				.keys(Collections.singleton(request.getKey()))
				.dataSlices(Collections.singletonList(new SliceDTO()))
				.build();

		when(dataProvider.retrieve(any(), any())).thenReturn(passengerData);

		ResponseEntity<PassengerData> response = controller.getPassengerData(request);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(passengerData, response.getBody());
	}
}

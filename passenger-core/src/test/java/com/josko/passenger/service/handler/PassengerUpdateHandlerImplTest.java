package com.josko.passenger.service.handler;

import com.josko.passenger.service.PassengerService;
import com.josko.passenger.service.mappers.KeyMapper;
import com.josko.passenger.service.slice.SliceService;
import com.josko.passenger.update.dto.PassengerUpdate;
import com.josko.passenger.update.slices.Slice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerUpdateHandlerImplTest {

	@Mock
	private PassengerService passengerService;

	@Mock
	private KeyMapper keyMapper;

	@Mock
	private SliceService<?> sliceService;

	private PassengerUpdateHandlerImpl passengerUpdateHandler;
	private PassengerUpdate passengerUpdate;
	private Slice slice;

	@BeforeEach
	void setUp() {
		passengerUpdateHandler = new PassengerUpdateHandlerImpl(passengerService, keyMapper, List.of(sliceService));
		passengerUpdate = new PassengerUpdate();
		slice = mock(Slice.class);
	}

	@Test
	void testHandle_NoPassengerIdAndEmptySlices() {
		when(passengerService.getPassengerID(any())).thenReturn(Optional.empty());
		passengerUpdateHandler.handle(passengerUpdate);

		verify(sliceService, never()).persistSlice(any(), any(), any());
		verify(passengerService, never()).createPassenger(any());
	}

	@Test
	void testHandle_PassengerIdAndEmptySlices() {
		when(passengerService.getPassengerID(any())).thenReturn(Optional.of(UUID.randomUUID()));
		passengerUpdateHandler.handle(passengerUpdate);

		verify(passengerService, never()).createPassenger(any());
		verify(sliceService, never()).persistSlice(any(), any(), any());
		verify(passengerService, times(1)).saveKeys(any(), any());
		verify(passengerService, times(1)).setPurgeTs(any());}

	@Test
	void testHandle_PassengerIdAndSlices() {
		when(passengerService.getPassengerID(any())).thenReturn(Optional.of(UUID.randomUUID()));
		passengerUpdate.setSlices(Collections.singletonList(slice));

		passengerUpdateHandler.handle(passengerUpdate);

		verify(passengerService, times(1)).getPassengerID(any());
		verify(sliceService, times(1)).persistSlice(any(), any(), any());
		verify(passengerService, times(1)).saveKeys(any(), any());
		verify(passengerService, times(1)).setPurgeTs(any());
	}
}

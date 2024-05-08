package com.josko.passenger.service.slice;

import com.josko.passenger.persistence.entity.BookingEntity;
import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.repository.BookingRepository;
import com.josko.passenger.persistence.repository.PassengerRepository;
import com.josko.passenger.service.mappers.BookingMapper;
import com.josko.passenger.update.dto.PassengerUpdate;
import com.josko.passenger.update.slices.BookingData;
import com.josko.passenger.update.slices.Slice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingSliceServiceTest {

	@Mock
	private PassengerRepository passengerRepository;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private BookingMapper bookingMapper;

	@InjectMocks
	private BookingSliceService bookingSliceService;


	private final UUID passengerId = UUID.randomUUID();
	private PassengerEntity passengerEntity;
	private BookingEntity bookingEntity;

	@BeforeEach
	void setUp() {
		passengerEntity = new PassengerEntity();
		passengerEntity.setPassengerId(passengerId);
		bookingEntity = new BookingEntity();
		bookingEntity.setPassenger(passengerEntity);
	}

	@Test
	void testDeleteSlice() {
		bookingSliceService.deleteSlice(passengerEntity);

		verify(bookingRepository).deleteByPassengerPassengerId(passengerId);
	}

	@Test
	void testPersistSlice() {
		var metaData = new PassengerUpdate();
		var bkgData = new BookingData();
		var slice = Slice.<BookingData>builder()
				.content(bkgData)
				.build();
		
		when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passengerEntity));
		when(bookingMapper.fromDto(passengerEntity, bkgData, metaData)).thenReturn(bookingEntity);

		bookingSliceService.persistSlice(passengerId, metaData, slice);

		verify(bookingRepository, times(1)).findByPassengerID(passengerId);
		verify(bookingRepository, times(1)).save(bookingEntity);
	}
}

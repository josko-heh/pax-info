package com.josko.passenger.service.slice;

import com.josko.passenger.persistence.entity.PassengerDetailsEntity;
import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.repository.PassengerDetailsRepository;
import com.josko.passenger.persistence.repository.PassengerRepository;
import com.josko.passenger.service.mappers.PassengerDetailsMapper;
import com.josko.passenger.update.dto.PassengerUpdate;
import com.josko.passenger.update.slices.PassengerDetailsData;
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
class DetailsSliceServiceTest {

	@Mock
	private PassengerRepository passengerRepository;

	@Mock
	private PassengerDetailsRepository detailsRepository;

	@Mock
	private PassengerDetailsMapper detailsMapper;

	@InjectMocks
	private DetailsSliceService detailsSliceService;


	private final UUID passengerId = UUID.randomUUID();
	private PassengerEntity passengerEntity;
	private PassengerDetailsEntity detailsEntity;

	@BeforeEach
	void setUp() {
		passengerEntity = new PassengerEntity();
		passengerEntity.setPassengerId(passengerId);
		detailsEntity = new PassengerDetailsEntity();
		detailsEntity.setPassenger(passengerEntity);
	}

	@Test
	void testDeleteSlice() {
		detailsSliceService.deleteSlice(passengerEntity);

		verify(detailsRepository).deleteByPassengerPassengerId(passengerId);
	}

	@Test
	void testPersistSlice() {
		var metaData = new PassengerUpdate();
		var detailsData = new PassengerDetailsData();
		var slice = Slice.<PassengerDetailsData>builder()
				.content(detailsData)
				.build();

		when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passengerEntity));
		when(detailsMapper.fromDto(passengerEntity, detailsData, metaData)).thenReturn(detailsEntity);

		detailsSliceService.persistSlice(passengerId, metaData, slice);

		verify(detailsRepository, times(1)).findByPassengerID(passengerId);
		verify(detailsRepository, times(1)).save(detailsEntity);
	}
}


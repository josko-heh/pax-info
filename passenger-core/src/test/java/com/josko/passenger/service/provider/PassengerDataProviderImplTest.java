package com.josko.passenger.service.provider;

import com.josko.passenger.exceptions.PassengerModuleException;
import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.presentation.dto.slices.PassengerData;
import com.josko.passenger.presentation.dto.slices.SliceDTO;
import com.josko.passenger.service.PassengerService;
import com.josko.passenger.service.mappers.KeyMapper;
import com.josko.passenger.service.mappers.SliceMapper;
import com.josko.passenger.update.dto.keys.KeyDTO;
import com.josko.passenger.update.dto.keys.PnrKeyDTO;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerDataProviderImplTest {

	@Mock
	private PassengerService passengerService;

	@Mock
	private SliceMapper sliceMapper;

	@Mock
	private KeyMapper keyMapper;

	@InjectMocks
	private PassengerDataProviderImpl passengerDataProvider;

	private final KeyDTO keyDTO = new PnrKeyDTO();
	private final List<SliceDTO.Type> dataSliceTypes = new ArrayList<>();
	private final UUID passengerId = UUID.randomUUID();
	private final Set<KeyEntity> keys = new HashSet<>();
	private Set<Slice> slices = new HashSet<>();

	@BeforeEach
	void setUp() {
		slices = new HashSet<>();
	}

	@Test
	void testRetrieve_NoPassengerFound() {
		when(passengerService.getPassengerID(any())).thenReturn(Optional.empty());

		assertThrows(PassengerModuleException.class, () -> passengerDataProvider.retrieve(dataSliceTypes, keyDTO));

		verify(passengerService, times(1)).getPassengerID(any());
		verify(keyMapper, times(1)).toEntity(any());
		verifyNoInteractions(sliceMapper);
	}

	@Test
	void testRetrieve_AllSlicesEmpty() {
		when(passengerService.getPassengerID(any())).thenReturn(Optional.of(passengerId));
		when(passengerService.retrieveSlices(any(), any())).thenReturn(slices);

		assertThrows(PassengerModuleException.class, () -> passengerDataProvider.retrieve(dataSliceTypes, keyDTO));

		verify(passengerService, times(1)).getPassengerID(any());
		verify(keyMapper, times(1)).toEntity(any());
		verify(passengerService, times(1)).retrieveSlices(any(), any());
		verify(sliceMapper, times(1)).map(any(List.class));
		verify(passengerService, times(1)).getPassengerKeys(any());
		verify(sliceMapper, never()).toDTOs(any());
		verify(keyMapper, never()).toDTOs(any());
	}

	@Test
	void testRetrieve_ValidData() {
		Slice slice = mock(Slice.class);
		SliceData sliceData = mock(SliceData.class);
		when(slice.getContent()).thenReturn(sliceData);
		when(sliceData.isEmpty()).thenReturn(false);
		slices.add(slice);
		
		when(passengerService.getPassengerID(any())).thenReturn(Optional.of(passengerId));
		when(passengerService.retrieveSlices(any(), any())).thenReturn(slices);
		when(sliceMapper.toDTOs(any())).thenReturn(Collections.singletonList(new SliceDTO()));
		when(passengerService.getPassengerKeys(any())).thenReturn(keys);
		when(keyMapper.toDTOs(any())).thenReturn(Collections.singleton(keyDTO));

		PassengerData result = passengerDataProvider.retrieve(dataSliceTypes, keyDTO);

		assertNotNull(result);
		assertEquals(passengerId, result.getPassengerId());
		assertFalse(result.getDataSlices().isEmpty());
		assertFalse(result.getKeys().isEmpty());

		verify(passengerService, times(1)).getPassengerID(any());
		verify(passengerService, times(1)).retrieveSlices(any(), any());
		verify(sliceMapper, times(1)).toDTOs(any());
		verify(passengerService, times(1)).getPassengerKeys(any());
		verify(keyMapper, times(1)).toDTOs(any());
	}
}

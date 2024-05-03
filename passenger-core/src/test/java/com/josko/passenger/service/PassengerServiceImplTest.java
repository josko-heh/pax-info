package com.josko.passenger.service;

import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.persistence.entity.keys.PnrKeyEntity;
import com.josko.passenger.persistence.repository.PassengerRepository;
import com.josko.passenger.service.provider.SliceProvider;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {

	@Mock
	private PassengerRepository repository;

	@Mock
	private SliceProvider<?> provider;

	private static final SliceData.Type type = SliceData.Type.DETAILS;
	private static final UUID paxIdFixture = UUID.randomUUID();
	private static final PassengerEntity passengerFixture = new PassengerEntity();
	private static final Set<KeyEntity> keysFixture = Collections.singleton(new PnrKeyEntity());

	private PassengerServiceImpl passengerService;

	@BeforeEach
	void setUp() {
		when(provider.accepts()).thenReturn(type);
		
		passengerService = new PassengerServiceImpl(repository, List.of(provider));
		
		passengerFixture.setPurgeTs(Instant.now().plus(10, ChronoUnit.DAYS));
		passengerFixture.setPassengerId(paxIdFixture);
	}
	

	@Test
	void testGetPassenger() {
		when(repository.findById(paxIdFixture)).thenReturn(Optional.of(passengerFixture));

		Optional<PassengerEntity> result = passengerService.getPassenger(paxIdFixture);

		assertEquals(passengerFixture, result.orElse(null));
	}

	@Test
	void testGetPassengerID() {
		when(repository.findPassengerIdByKey(keysFixture.stream().findFirst().get())).thenReturn(Optional.of(paxIdFixture));

		Optional<UUID> result = passengerService.getPassengerID(keysFixture);

		assertEquals(paxIdFixture, result.orElse(null));
	}

	@Test
	void testGetPassengerKeys() {
		when(repository.findKeys(paxIdFixture)).thenReturn((keysFixture));

		Set<KeyEntity> result = passengerService.getPassengerKeys(paxIdFixture);

		assertEquals(keysFixture, result);
	}

	@Test
	void testRetrieveSlices() {
		Slice slice = mock(Slice.class);
		when(provider.provide(paxIdFixture)).thenReturn(slice);

		Set<Slice> result = passengerService.retrieveSlices(paxIdFixture, Collections.singleton(type));

		assertTrue(result.contains(slice));
	}

	@Test
	void testSetPurgeTs() {
		passengerService.setPurgeTs(paxIdFixture);

		verify(repository).updatePassengerPurgeTs(any(), eq(paxIdFixture));
	}

	@Test
	void testCreatePassenger() {
		Answer<PassengerEntity> saveAnswer = invocation -> {
			PassengerEntity argument = invocation.getArgument(0);
			argument.setPassengerId(paxIdFixture);
			return argument;
		};

		when(repository.save(any())).thenAnswer(saveAnswer);

		PassengerEntity ignored = passengerService.createPassenger(keysFixture);
		
		verify(repository, times(1)).save(any());
	}

	@Test
	void testSaveKeys() {
		when(repository.findKeys(paxIdFixture)).thenReturn(Collections.emptySet());
		when(repository.findById(paxIdFixture)).thenReturn(Optional.of(passengerFixture));

		passengerService.saveKeys(paxIdFixture, keysFixture);

		verify(repository).saveKey(any());
	}

	@Test
	void testSaveKeys_NoNewKeys() {
		when(repository.findKeys(paxIdFixture)).thenReturn(keysFixture);

		passengerService.saveKeys(paxIdFixture, keysFixture);

		verify(repository, never()).findById(any());
		verify(repository, never()).saveKey(any());
	}
}

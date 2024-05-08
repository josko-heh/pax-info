package com.josko.passenger.persistence.repository;


import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.persistence.entity.keys.PnrKeyEntity;
import com.josko.passenger.persistence.entity.keys.TicketNumberKeyEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeySupportImplTest {

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private KeySupportImpl keySupport;

	@Mock
	private CriteriaBuilder cb;
	@Mock
	private CriteriaQuery<UUID> cq;
	@Mock
	private TypedQuery<UUID> typedQuery;
	@Mock
	private Path<UUID> passengerIdPath;
	
	private static final UUID passengerId = UUID.randomUUID();
	private static final TicketNumberKeyEntity ticketNumberKey = new TicketNumberKeyEntity();
	private static final PnrKeyEntity pnrKey = new PnrKeyEntity();

	@BeforeAll
	static void setUp() {
		ticketNumberKey.setTicketNumber("123456");
		ticketNumberKey.setCarrier("ABC");
		ticketNumberKey.setFlightNumber("123");
		ticketNumberKey.setDepartureDate(LocalDate.EPOCH);

		pnrKey.setLocator("XYZ123");
		pnrKey.setFirstName("John");
		pnrKey.setLastName("Doe");
	}

	@Test
	void testFindPassengerIdByKey_TicketNumberKeyEntity() {
		setUpMocksForFindPassengerIdByKey();
		
		Root<TicketNumberKeyEntity> root = mock(Root.class);

		when(cq.from(TicketNumberKeyEntity.class)).thenReturn(root);
		when(root.get(isNull(SingularAttribute.class))).thenReturn(passengerIdPath);

		Optional<UUID> result = keySupport.findPassengerIdByKey(ticketNumberKey);

		assertEquals(passengerId, result.orElse(null));
	}

	@Test
	void testFindPassengerIdByKey_PnrKeyEntity() {
		setUpMocksForFindPassengerIdByKey();
		
		Root<PnrKeyEntity> root = mock(Root.class);

		when(cq.from(PnrKeyEntity.class)).thenReturn(root);
		when(root.get(isNull(SingularAttribute.class))).thenReturn(passengerIdPath);

		Optional<UUID> result = keySupport.findPassengerIdByKey(pnrKey);

		assertEquals(passengerId, result.orElse(null));
	}

	@Test
	void testFindKeys() {
		CriteriaQuery<TicketNumberKeyEntity> cqTicket = mock(CriteriaQuery.class);
		CriteriaQuery<PnrKeyEntity> cqPnr = mock(CriteriaQuery.class);
		Root<TicketNumberKeyEntity> rootTicket = mock(Root.class);
		Root<PnrKeyEntity> rootPnr = mock(Root.class);
		TypedQuery<TicketNumberKeyEntity> typedQueryTicket = mock(TypedQuery.class);
		TypedQuery<PnrKeyEntity> typedQueryPnr = mock(TypedQuery.class);

		when(entityManager.getCriteriaBuilder()).thenReturn(cb);
		when(cb.createQuery(TicketNumberKeyEntity.class)).thenReturn(cqTicket);
		when(cqTicket.from(TicketNumberKeyEntity.class)).thenReturn(rootTicket);
		when(rootTicket.get(isNull(SingularAttribute.class))).thenReturn(passengerIdPath);
		when(cqTicket.select(rootTicket)).thenReturn(cqTicket);
		when(entityManager.createQuery(cqTicket)).thenReturn(typedQueryTicket);
		when(typedQueryTicket.getResultList()).thenReturn(Collections.singletonList(ticketNumberKey));

		when(cb.createQuery(PnrKeyEntity.class)).thenReturn(cqPnr);
		when(cqPnr.from(PnrKeyEntity.class)).thenReturn(rootPnr);
		when(rootPnr.get(isNull(SingularAttribute.class))).thenReturn(passengerIdPath);
		when(cqPnr.select(rootPnr)).thenReturn(cqPnr);
		when(entityManager.createQuery(cqPnr)).thenReturn(typedQueryPnr);
		when(typedQueryPnr.getResultList()).thenReturn(Collections.singletonList(pnrKey));

		Set<KeyEntity> result = keySupport.findKeys(passengerId);

		assertTrue(result.contains(ticketNumberKey));
		assertTrue(result.contains(pnrKey));
		assertEquals(2, result.size());
	}

	@Test
	void testSaveKey() {
		keySupport.saveKey(ticketNumberKey);
		verify(entityManager).persist(ticketNumberKey);
	}

	private void setUpMocksForFindPassengerIdByKey() {
		when(cq.select(any())).thenReturn(cq);
		when(cb.createQuery(UUID.class)).thenReturn(cq);
		when(entityManager.getCriteriaBuilder()).thenReturn(cb);
		when(typedQuery.getResultList()).thenReturn(Collections.singletonList(passengerId));
		when(entityManager.createQuery(cq)).thenReturn(typedQuery);
	}
}

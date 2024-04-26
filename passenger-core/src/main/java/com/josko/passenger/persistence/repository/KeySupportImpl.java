package com.josko.passenger.persistence.repository;

import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.persistence.entity.PassengerEntity_;
import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.persistence.entity.keys.KeyEntity_;
import com.josko.passenger.persistence.entity.keys.TicketNumberKeyEntity;
import com.josko.passenger.persistence.entity.keys.TicketNumberKeyEntity_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Repository
@Transactional
public class KeySupportImpl implements KeySupport {

    @PersistenceContext
    private EntityManager entityManager;
    

    @Override
    public Optional<PassengerEntity> findByKey(KeyEntity key, Class<? extends KeyEntity> type) {
        
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        final CriteriaQuery<PassengerEntity> cq = cb.createQuery(PassengerEntity.class);
        
        final Predicate clause;

        final Path<PassengerEntity> from;
            
        if(TicketNumberKeyEntity.class.isAssignableFrom(type)) {
            Root<TicketNumberKeyEntity> root = cq.from(TicketNumberKeyEntity.class);

            from = root.get(TicketNumberKeyEntity_.passenger);

            clause = getPredicate((TicketNumberKeyEntity) key, cb, root);

        } else {
            throw new UnsupportedOperationException("Implement me!");
        }
        
        cq.select(from).where(clause);
        
        return entityManager.createQuery(cq).getResultList().stream().findFirst();
    }

    @Override
    public Optional<UUID> findPassengerIdByKey(KeyEntity key, Class<? extends KeyEntity> type) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<UUID> cq = cb.createQuery(UUID.class);
        final Predicate clause;
        final Path<UUID> from;
            
        if(TicketNumberKeyEntity.class.isAssignableFrom(type)) {
            Root<TicketNumberKeyEntity> root = cq.from(TicketNumberKeyEntity.class);

            from = root.get(TicketNumberKeyEntity_.passenger).get(PassengerEntity_.passengerId);

            clause = getPredicate((TicketNumberKeyEntity) key, cb, root);
        } else {
            throw new UnsupportedOperationException("Implement me!");
        }
        
        cq.select(from).where(clause);
        
        return entityManager.createQuery(cq).getResultList().stream().findFirst();
    }

    private Predicate getPredicate(TicketNumberKeyEntity key, CriteriaBuilder cb, Root<TicketNumberKeyEntity> root) {
        Predicate clause;

		final List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get(TicketNumberKeyEntity_.ticketNumber), key.getTicketNumber()));

        if(isNotBlank(key.getCarrier())){
            predicates.add(cb.equal(root.get(TicketNumberKeyEntity_.carrier), key.getCarrier()));
        }

        if(isNotBlank(key.getFlightNumber())) {
            predicates.add(cb.equal(root.get(TicketNumberKeyEntity_.flightNumber), key.getFlightNumber()));
        }

        if(key.getDepartureDate() != null) {
            predicates.add(cb.equal(root.get(TicketNumberKeyEntity_.departureDate), key.getDepartureDate()));
        }

        clause = cb.and(predicates.toArray(new Predicate[0]));
        return clause;
    }


    @Override
    public List<KeyEntity> findKeys(PassengerEntity passenger) {
        return findKeys(passenger.getPassengerId());
    }

    @Override
    public List<KeyEntity> findKeys(UUID passengerId) {
        final var keyTypes = Set.of(TicketNumberKeyEntity.class);

        final var cb = entityManager.getCriteriaBuilder();
        final var results = new ArrayList<KeyEntity>();

        for (Class keyType : keyTypes) {
            final var cq = cb.createQuery(keyType);
            final var root = cq.from(keyType);
            final var clause = cb.and(
                    cb.equal(root.get(KeyEntity_.passenger).get(PassengerEntity_.passengerId), passengerId));

            cq.select(root).where(clause);

            results.addAll(entityManager.createQuery(cq).getResultList());
        }

        return results;
    }

    @Override
    public void saveKey(KeyEntity key) {
        entityManager.persist(key);
    }
}

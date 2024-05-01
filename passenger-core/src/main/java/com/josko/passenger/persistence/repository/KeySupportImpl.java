package com.josko.passenger.persistence.repository;

import com.josko.passenger.persistence.entity.PassengerEntity_;
import com.josko.passenger.persistence.entity.keys.*;
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
    public Optional<UUID> findPassengerIdByKey(KeyEntity key) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<UUID> cq = cb.createQuery(UUID.class);
        final Predicate clause;
        final Path<UUID> from;
            
        if(key instanceof TicketNumberKeyEntity tkneKey) {
            Root<TicketNumberKeyEntity> root = cq.from(TicketNumberKeyEntity.class);

            from = root.get(KeyEntity_.passenger).get(PassengerEntity_.passengerId);

            clause = getPredicate(tkneKey, cb, root);
            
        } else if(key instanceof PnrKeyEntity pnrKey){
            Root<PnrKeyEntity> root = cq.from(PnrKeyEntity.class);

            from = root.get(KeyEntity_.passenger).get(PassengerEntity_.passengerId);

            var equalLocator = cb.equal(root.get(PnrKeyEntity_.locator), pnrKey.getLocator());
            var equalFirstName = cb.equal(root.get(PnrKeyEntity_.firstName), pnrKey.getFirstName());
            var equalLastName = cb.equal(root.get(PnrKeyEntity_.lastName), pnrKey.getLastName());

            clause = cb.and(equalLocator, equalFirstName, equalLastName);

        } else {
            throw new UnsupportedOperationException("Not implemented!");
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
    public List<KeyEntity> findKeys(UUID passengerId) {
        final var keyTypes = Set.of(TicketNumberKeyEntity.class, PnrKeyEntity.class);

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

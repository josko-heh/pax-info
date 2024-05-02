package com.josko.passenger.service;

import com.josko.passenger.persistence.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class PassengerPurger {

    private final PassengerRepository passengerRepository;

    @Scheduled(cron = "${purging.cronExpression:* */15 * * * *}")
    public void schedulePaxPurgeProcess() {
        log.info("Purging passengers");
        passengerRepository.purgePassengers(Instant.now());
    }

}

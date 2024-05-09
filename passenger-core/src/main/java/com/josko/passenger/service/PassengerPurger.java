package com.josko.passenger.service;

import com.josko.passenger.config.Definitions;
import com.josko.passenger.persistence.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class PassengerPurger {

    private final Logger debugLog = LogManager.getLogger(Definitions.DEBUG_LOGGER);
    
    private final PassengerRepository passengerRepository;

    @Scheduled(cron = "${purging.cronExpression:0 */10 * * * *}")
    public void schedulePaxPurgeProcess() {
        debugLog.info("Purging passengers");
        passengerRepository.purgePassengers(Instant.now());
    }

}

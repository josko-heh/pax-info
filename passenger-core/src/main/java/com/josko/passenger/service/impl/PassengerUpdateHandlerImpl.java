package com.josko.passenger.service.impl;

import com.josko.passenger.update.dto.PassengerUpdate;
import com.josko.passenger.service.PassengerService;
import com.josko.passenger.service.PassengerUpdateHandler;
import com.josko.passenger.service.SliceHandler;
import com.josko.passenger.service.mappers.KeyMapper;
import com.josko.passenger.update.slices.SliceData;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.josko.passenger.config.Definitions.PAX_ID_MDC;

@Component
@Log4j2
public class PassengerUpdateHandlerImpl implements PassengerUpdateHandler {

    private final PassengerService passengerService;
    private final KeyMapper keyMapper;

    private final Map<SliceData.Type, SliceHandler<?>> sliceHandlers;

    public PassengerUpdateHandlerImpl(PassengerService passengerService, KeyMapper keyMapper,
                                      List<SliceHandler<?>> sliceHandlers) {
        this.passengerService = passengerService;
        this.keyMapper = keyMapper;
        this.sliceHandlers = sliceHandlers.stream().collect(
                Collectors.toMap(SliceHandler::accepts, Function.identity()));
    }

    @Override
    public void handle(PassengerUpdate passengerUpdate) {
        final var keys = keyMapper.toEntities(passengerUpdate.getKeys());
        final var slices = passengerUpdate.getSlices();

        var passengerIDOpt = passengerService.getPassengerID(keys);

        if (passengerIDOpt.isEmpty() && slices.isEmpty())
            return;
        
        log.debug("Handling update for passenger with keys {}", passengerUpdate.getKeys().stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));

        final var passengerId = passengerIDOpt
                .orElseGet(() -> passengerService.createPassenger(keys).getPassengerId());

        try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.put(PAX_ID_MDC, passengerId.toString())) {
            slices.stream()
                    .filter(slice -> sliceHandlers.containsKey(slice.getName()))
                    .forEach(slice -> sliceHandlers.get(slice.getName())
                                                .handle(passengerId, passengerUpdate, slice));

            passengerService.saveKeys(passengerId, keys);
            passengerService.setPaxPurgeDate(passengerId);
        }
    }
}

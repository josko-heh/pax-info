package com.josko.passenger.service.handler;

import com.josko.passenger.config.Definitions;
import com.josko.passenger.service.PassengerService;
import com.josko.passenger.service.mappers.KeyMapper;
import com.josko.passenger.service.slice.SliceService;
import com.josko.passenger.update.dto.PassengerUpdate;
import com.josko.passenger.update.slices.SliceData;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.josko.passenger.config.Definitions.PAX_ID_MDC;

@Component
public class PassengerUpdateHandlerImpl implements PassengerUpdateHandler {
    
    private final Logger debugLog = LogManager.getLogger(Definitions.DEBUG_LOGGER);

    private final PassengerService passengerService;
    private final KeyMapper keyMapper;

    private final Map<SliceData.Type, SliceService<?>> sliceServices;

    public PassengerUpdateHandlerImpl(PassengerService passengerService, KeyMapper keyMapper,
                                      List<SliceService<?>> sliceServices) {
        this.passengerService = passengerService;
        this.keyMapper = keyMapper;
        this.sliceServices = sliceServices.stream().collect(
                Collectors.toMap(SliceService::accepts, Function.identity()));
    }

    @Override
    public void handle(PassengerUpdate passengerUpdate) {
        final var keys = keyMapper.toEntities(passengerUpdate.getKeys());
        final var slices = passengerUpdate.getSlices();

        var passengerIDOpt = passengerService.getPassengerID(keys);

        if (passengerIDOpt.isEmpty() && slices.isEmpty())
            return;

        debugLog.debug("Handling update for passenger with keys {}", passengerUpdate.getKeys().stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));

        final var passengerId = passengerIDOpt
                .orElseGet(() -> passengerService.createPassenger(keys).getPassengerId());

        try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.put(PAX_ID_MDC, passengerId.toString())) {
            slices.stream()
                    .filter(slice -> sliceServices.containsKey(slice.getName()))
                    .forEach(slice -> sliceServices.get(slice.getName())
                                                .persistSlice(passengerId, passengerUpdate, slice));

            // execute only on update, not create
            if (passengerIDOpt.isPresent()) {
                passengerService.saveKeys(passengerId, keys);
                passengerService.setPurgeTs(passengerId);
            }
        }
    }
}

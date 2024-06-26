package com.josko.passenger.process;

import com.josko.passenger.config.Definitions;
import com.josko.passenger.service.handler.PassengerUpdateHandler;
import com.josko.passenger.update.dto.PassengerUpdate;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Consumes passenger events triggered from adapters.
 */
@Component
@RequiredArgsConstructor
public class PassengerEventsConsumer extends RouteBuilder {

    private static final String ERROR_ROUTE = "direct:errorRoute";
   
    private final Logger errorLog = LogManager.getLogger(Definitions.ERROR_LOGGER);
    private final Logger debugLog = LogManager.getLogger(Definitions.DEBUG_LOGGER);

    @Value("${passenger.update.jms.source}")
    private String source;

    private final PassengerUpdateHandler eventProcessor;

    private final Processor logError = exchange -> {
        PassengerUpdate update = exchange.getIn().getBody(PassengerUpdate.class);
        var ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        errorLog.error( "Error while handling update from datasource {}.", update.getDatasource(), ex);
    };

    @Override
    public void configure() {
        errorHandler(deadLetterChannel(ERROR_ROUTE));

        from(ERROR_ROUTE).process(logError);
        
        from(source).process(exchange -> {
            PassengerUpdate update = exchange.getIn().getBody(PassengerUpdate.class);
            debugLog.debug("Received new update with reference {} from {}@{}",
                    update.getReference(), update.getDatasource(), update.getUpdateTs());
            eventProcessor.handle(update);
        });
    }
}

-- liquibase formatted sql
-- changeset josko:create_tkne_table

CREATE TABLE pax_data_passenger_key_tkne
(
	passenger_id        binary(16) NOT NULL,
	carrier             char(3)     NOT NULL,
	flight_number       varchar(4)  NOT NULL,
	scheduled_departure date        NOT NULL,
	ticket_number       varchar(32) NOT NULL,
	PRIMARY KEY (passenger_id, carrier, flight_number, scheduled_departure, ticket_number),
	KEY                 ticket_number (ticket_number),
	CONSTRAINT fk_pax_data_passenger_key_tkne_passenger FOREIGN KEY (passenger_id)
		REFERENCES pax_data_passenger (passenger_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

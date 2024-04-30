-- liquibase formatted sql
-- changeset josko:create_booking_table

CREATE TABLE pax_data_booking
(
	booking_id      binary(16)  NOT NULL,
	passenger_id    binary(16)  NOT NULL,
	locator         char(7)     NOT NULL,
	tattoo          tinyint     NULL,
	point_of_sale   varchar(32)          DEFAULT NULL,
	created_ts      timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_ts      timestamp   NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	datasource_name varchar(45) NOT NULL,
	purge_ts        timestamp   NULL     DEFAULT NULL,
	PRIMARY KEY (booking_id),
	KEY fk_pax_data_booking_passenger_idx (passenger_id),
	CONSTRAINT fk_pax_data_booking_passenger FOREIGN KEY (passenger_id)
		REFERENCES pax_data_passenger (passenger_id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

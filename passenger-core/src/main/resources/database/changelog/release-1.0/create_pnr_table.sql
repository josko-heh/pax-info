-- liquibase formatted sql
-- changeset josko:create_pnr_table

CREATE TABLE pax_data_passenger_key_pnr
(
	passenger_id binary(16)   NOT NULL,
	locator      char(7)      NOT NULL,
	first_name   varchar(200) NOT NULL,
	last_name    varchar(200) NOT NULL,
	PRIMARY KEY (locator, last_name, first_name),
	KEY locator_idx (locator),
	CONSTRAINT fk_pax_data_passenger_key_pnr_passenger FOREIGN KEY (passenger_id)
		REFERENCES pax_data_passenger (passenger_id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

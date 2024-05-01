-- liquibase formatted sql
-- changeset josko:alter_locator

ALTER TABLE pax_data_passenger_key_pnr
	MODIFY COLUMN locator CHAR(6) NOT NULL;
ALTER TABLE pax_data_booking
	MODIFY COLUMN locator CHAR(6) NOT NULL;

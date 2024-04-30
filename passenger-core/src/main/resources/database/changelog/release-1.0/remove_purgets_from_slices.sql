-- liquibase formatted sql
-- changeset josko:remove_purgets_from_slices

ALTER TABLE pax_data_booking
	DROP COLUMN purge_ts;
ALTER TABLE pax_data_passenger_details
	DROP COLUMN purge_ts;
	

-- liquibase formatted sql
-- changeset josko:create_passenger_table

CREATE TABLE IF NOT EXISTS pax_data_passenger
(
	passenger_id BINARY(16) NOT NULL,
	created_ts   TIMESTAMP  NOT NULL,
	updated_ts   TIMESTAMP  NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	purge_ts     TIMESTAMP  NOT NULL,
	PRIMARY KEY (passenger_id)
)
	ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

-- liquibase formatted sql
-- changeset josko:create_passenger_details_table

CREATE TABLE pax_data_passenger_details
(
	details_id        binary(16)  NOT NULL,
	passenger_id      binary(16)  NOT NULL,
	first_name        varchar(64)          DEFAULT NULL,
	last_name         varchar(64)          DEFAULT NULL,
	title             varchar(15)          DEFAULT NULL,
	gender            varchar(15)          DEFAULT NULL,
	date_of_birth     date                 DEFAULT NULL,
	city              varchar(32)          DEFAULT NULL,
	zip_code          varchar(6)           DEFAULT NULL,
	street_and_number varchar(45)          DEFAULT NULL,
	country           char(2)              DEFAULT NULL,
	language          char(2)              DEFAULT NULL,
	created_ts        timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_ts        timestamp   NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	datasource_name   varchar(45) NOT NULL,
	purge_ts          timestamp   NULL     DEFAULT NULL,
	PRIMARY KEY (details_id),
	KEY fk_pax_data_passenger_details_passenger (passenger_id),
	CONSTRAINT fk_pax_data_passenger_details_passenger FOREIGN KEY (passenger_id)
		REFERENCES pax_data_passenger (passenger_id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

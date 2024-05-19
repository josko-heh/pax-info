-- liquibase formatted sql
-- changeset josko:create_security_tables

CREATE TABLE IF NOT EXISTS pax_data_security_authority
(
	id   bigint      NOT NULL,
	name varchar(55) NOT NULL,
	PRIMARY KEY (id)
)
	ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS pax_data_security_user
(
	id         bigint       NOT NULL,
	username   varchar(100) NOT NULL UNIQUE,
	password   varchar(250) NOT NULL,
	first_name varchar(100) NOT NULL,
	last_name  varchar(100) NOT NULL,
	PRIMARY KEY (id)
)
	ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS pax_data_security_user_authority
(
	user_id      bigint NOT NULL,
	authority_id bigint NOT NULL,
	PRIMARY KEY (user_id, authority_id)
)
	ENGINE = InnoDB
	DEFAULT CHARACTER SET = utf8;


INSERT INTO pax_data_security_authority (id, name) VALUES (1, 'ROLE_USER');

INSERT INTO pax_data_security_user (id, username, password, first_name, last_name)
VALUES (1, 'generaluser', '899itdiPss', 'API', 'User');

INSERT INTO pax_data_security_user_authority (user_id, authority_id) VALUES (1, 1);

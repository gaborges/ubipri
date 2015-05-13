# Environments schema
 
# --- !Ups

CREATE TABLE device_types (
	id serial not null primary key,
	name varchar(100) not null
);
 
# --- !Downs
 
DROP TABLE device_types;
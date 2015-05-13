# functionality schema
 
# --- !Ups

CREATE TABLE functionalities (
	id serial not null primary key,
	name varchar(100) not null
);

# --- !Downs
 
DROP TABLE functionalities;
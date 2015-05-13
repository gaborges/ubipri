# Communication types schema
 
# --- !Ups

CREATE TABLE communication_types (
	id serial not null primary key,
	name varchar(100) not null
);

# --- !Downs
 
DROP TABLE communication_types;
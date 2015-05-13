# Environment types schema
 
# --- !Ups
 
CREATE TABLE environment_types (
	id serial not null primary key,
	name varchar(100) not null
);
 
# --- !Downs
 
DROP TABLE environment_types;
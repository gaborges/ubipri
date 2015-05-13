# Users schema
 
# --- !Ups

CREATE TABLE users (
	id 	serial not null primary key,
	name varchar(100) not null unique,
	password varchar(100) not null,
	full_name character varying(120),
	
	user_type_id integer not null,
	current_environment_id integer,
	
	foreign key (user_type_id) references user_types (id),
	foreign key (current_environment_id) references environments (id)
);
 
# --- !Downs
 
DROP TABLE users;
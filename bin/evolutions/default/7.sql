# Environments schema
 
# --- !Ups

CREATE TABLE devices (
	id  serial not null primary key,
	code varchar(50) not null unique,
	name varchar(100) not null,
	
	device_type_id integer not null,
	user_id integer,
	current_environment_id integer,
	
	foreign key (device_type_id) references device_types (id),
	foreign key (user_id) references users (id),
	foreign key (current_environment_id) references environments (id)
);

# --- !Downs
 
DROP TABLE devices;
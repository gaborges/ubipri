# Device communications schema
 
# --- !Ups

CREATE TABLE device_communications (
	id serial not null primary key,
	name varchar(100) not null,
	address varchar(100) not null,
	parameters text,
	address_format varchar(100),
	port varchar(50),
	
	device_id integer not null,
	communication_type_id integer not null,
	preferred_order integer not null default 1,
	
	foreign key (device_id) references devices (id),
	foreign key (communication_type_id) references communication_types (id)
);

# --- !Downs
 
DROP TABLE device_communications;
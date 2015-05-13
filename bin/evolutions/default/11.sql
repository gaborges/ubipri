# Device communications schema
 
# --- !Ups

CREATE TABLE device_functionalities (
	device_id integer not null,
	functionality_id integer not null,
	primary key (device_id, functionality_id),
	foreign key (device_id) references devices (id),
	foreign key (functionality_id) references functionalities (id)
);

# --- !Downs
 
DROP TABLE device_functionalities;
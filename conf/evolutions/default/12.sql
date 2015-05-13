# environment_points schema
 
# --- !Ups

CREATE TABLE environment_points (
	id serial not null primary key,
	latitude double precision not null,
	longitude double precision not null,
	altitude double precision not null default 0.0,
	point_order integer,
	
	environment_id integer not null,
	
	foreign key (environment_id) references environments (id)
);

# --- !Downs
 
DROP TABLE environment_points;
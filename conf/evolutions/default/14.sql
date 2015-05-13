# access_levels schema
 
# --- !Ups

CREATE TABLE access_levels (
	id serial not null primary key,	
	impact_factor double precision not null,
	
	environment_type_id integer not null,
	access_type_id integer not null,
	
	foreign key (environment_type_id) references environment_types (id),
	foreign key (access_type_id) references access_types (id)
);

# --- !Downs
 
DROP TABLE access_levels;
# Environments schema
 
# --- !Ups
 
CREATE TABLE environments (
	id serial not null primary key,
	name varchar(100) not null,
	latitude double precision not null,
	longitude double precision not null,
	altitude double precision not null default 0.0,
	operating_range double precision not null default 1.0,
	version integer not null default 0,
	
	parent_environment_id integer,
	environment_type_id integer not null,
	localization_type_id integer NOT NULL,
	
	foreign key (parent_environment_id) references environments (id),
	foreign key (environment_type_id) references environment_types (id),
	foreign key (localization_type_id) references localization_types (id)
);
 
# --- !Downs
 
DROP TABLE environments;
# user_in_environment schema
 
# --- !Ups

CREATE TABLE user_in_environment (
	id serial not null unique,
	
	user_id integer not null,
	user_profile_environment_id integer not null default 1, -- 1 is Unknown for the environment
	environment_id integer not null,
	impact_factor double precision default 0.0,
	current_access_type_id integer not null,
	
	primary key (user_id,environment_id),
	
	foreign key (user_profile_environment_id)
		references user_profile_environments (id),
	foreign key (environment_id) references environments (id),
	foreign key (user_id) references users (id),
	foreign key (current_access_type_id) references access_types (id)
);

# --- !Downs
 
DROP TABLE user_in_environment;
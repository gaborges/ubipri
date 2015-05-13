# actions_args schema
 
# --- !Ups

CREATE TABLE actions_args (
	id serial not null primary key,
	label varchar(100) default null,
	arg_value varchar(100) not null,
	
	action_id integer not null,
	
	foreign key (action_id) references actions (id)
);

# --- !Downs
 
DROP TABLE actions_args;
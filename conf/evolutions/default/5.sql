# Users schema
 
# --- !Ups

CREATE TABLE users (
	id 	serial not null primary key,
	name varchar(100) not null unique,
	full_name character varying(120),
	
	auth_token varchar(255),
    email_address varchar(256) not null,
    sha_password bytea not null,
	
	user_type_id integer not null,
	current_environment_id integer,
	
	constraint uq_user_email_address unique (email_address),
	foreign key (user_type_id) references user_types (id),
	foreign key (current_environment_id) references environments (id)
);
 
# --- !Downs
 
DROP TABLE users;
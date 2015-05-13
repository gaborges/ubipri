# user_profile_environments schema
 
# --- !Ups

CREATE TABLE user_profile_environments (
	id serial not null primary key,
	name varchar(100) not null
);

# --- !Downs
 
DROP TABLE user_profile_environments;
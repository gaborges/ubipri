# Localization types schema
 
# --- !Ups
 
CREATE TABLE localization_types (
  id serial NOT NULL primary key,
  name character varying(100) NOT NULL,
  precision double precision not null default 5.0,
  metric varchar(20) not null default 'm2'
);
 
# --- !Downs
 
DROP TABLE localization_types;
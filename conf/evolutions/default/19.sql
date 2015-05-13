# log_events schema
 
# --- !Ups

CREATE TABLE log_events (
	id serial not null primary key,
	log_time timestamp not null default now(),
	shift char(1), -- 'd' for diurnal shift, 'n' for nocturnal shift 
	weekday int, -- 1 for yes, 2 for not
	workday char(1), -- diz seo dia é dia útil ou se é um feriado, paralização ou final de semana que não se pode trabalhae. y for yes, n for not
	event varchar(100) not null,
	code varchar(100),
	
	user_id integer,
	device_id integer,
	environment_id integer not null,
	
	foreign key (environment_id) references environments (id),
	foreign key (user_id) references users (id),
	foreign key (device_id) references devices (id)
);

# --- !Downs
 
DROP TABLE log_events;
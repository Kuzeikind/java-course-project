--DROP DATABASE IF EXISTS park;
--
--CREATE DATABASE park;
--\c park
--CREATE SCHEMA rangers;
--SET search_path='rangers';

/*
Rangers tables. Creation and filling.
*/

DROP TABLE IF EXISTS ranger_rank CASCADE;
CREATE TABLE ranger_rank (
	id smallserial PRIMARY KEY,
	rank_name varchar
);

INSERT INTO ranger_rank (rank_name) VALUES
    ('JUNIOR'),
    ('MIDDLE'),
    ('SENIOR'),
    ('LEAD')
;

DROP TABLE IF EXISTS ranger CASCADE;
CREATE TABLE ranger (
	id bigserial PRIMARY KEY,
	first_name varchar NOT NULL,
	last_name varchar NOT NULL,
	email varchar NOT NULL UNIQUE,
    rank smallint REFERENCES ranger_rank(id) DEFAULT 1
);

DROP TABLE IF EXISTS ranger_passwords;
CREATE TABLE ranger_passwords (
	id bigserial REFERENCES ranger(id),
	password varchar NOT NULL
);

INSERT INTO ranger (first_name, last_name, email, rank) VALUES
    ('Ivan', 'Petrov', 'evanpetrov@mail.ru', 1),
    ('John', 'Smith', 'johnsmith@yahoo.com', 2),
    ('Jane', 'Doe', 'janedoe@gmail.com', 3),
    ('Cordell', 'Walker', 'cordellwalker@gmail.com', 4);

INSERT INTO ranger_passwords (password) VALUES
    ('password1'),
    ('password2'),
    ('password3'),
    ('password4');

/*
Tasks tables. Creation and filling.
*/

DROP TABLE IF EXISTS task_type CASCADE;
CREATE TABLE task_type (
	id smallserial PRIMARY KEY,
	type_name varchar
);

INSERT INTO task_type (type_name) VALUES
    ('PLANT'),
    ('REPAIR'),
    ('DECORATE'),
    ('REMOVE')
;

DROP TABLE IF EXISTS task_priority CASCADE;
CREATE TABLE task_priority (
	id smallserial PRIMARY KEY,
	priority_name varchar
);

INSERT INTO task_priority (priority_name) VALUES
    ('LOW'),
    ('MEDIUM'),
    ('HIGH'),
    ('CRITICAL')
;

DROP TABLE IF EXISTS task;
CREATE TABLE task (
	id bigserial PRIMARY KEY,
	assigned_to bigint REFERENCES ranger(id),
	priority smallint REFERENCES task_type(id),
	type smallint REFERENCES task_priority(id),
	description text,
	latitude double precision,
	longitude double precision,
	created_at timestamp
);

INSERT INTO task (assigned_to, priority, type, description, latitude, longitude, created_at) VALUES
    (3,    4, 1, 'Plant some flowers FAST!!!'              , 30.31, 59.95, current_timestamp - '1 mon'::interval),
    (3,    3, 2, 'Birch grove requires anti-insect care'   , 30.31, 59.95, current_timestamp - '3 days'::interval),
    (3,    1, 3, 'Xmass tree decoration should be started' , 30.31, 59.95, current_timestamp - '3 years'::interval),
    (3,    3, 1, 'Plant some cucumbers'                    , 30.31, 59.95, current_timestamp - '20 days'::interval),
    (3,    2, 3, 'And then decorate your cucumbers'        , 30.31, 59.95, current_timestamp - '19 days'::interval),
    (4,    1, 3, 'Decorate whatever you want'              , 30.31, 59.95, current_timestamp - '2 years'::interval),
    (4,    3, 3, 'Do some bansay'                          , 30.31, 59.95, current_timestamp - '10 days'::interval),
    (2,    1, 4, 'Illegal weeds must be destroyed'         , 30.31, 59.95, current_timestamp - '20 years'::interval),
    (2,    1, 4, 'Another task for ranger #2'              , 30.31, 59.95, current_timestamp - '5 days'::interval),
    (NULL, 3, 1, 'Another task for the planter'            , 30.31, 59.95, current_timestamp - '10 mons'::interval),
    (NULL, 3, 1, 'Plant some x-mass trees'                 , 30.31, 59.95, current_timestamp - '3 days'::interval),
    (NULL, 2, 2, 'Backyard lawn requires repairment'       , 30.31, 59.95, current_timestamp - '15 days'::interval),
    (NULL, 4, 2, 'A tree broken by a hurricane'            , 30.31, 59.95, current_timestamp - '16 days'::interval)
;

DROP TABLE IF EXISTS history;
CREATE TABLE history (
	id bigint PRIMARY KEY,
	assigned_to bigint REFERENCES ranger(id),
	priority smallint REFERENCES task_type(id),
	type smallint REFERENCES task_priority(id),
	description text,
	latitude double precision,
	longitude double precision,
	created_at timestamp
);



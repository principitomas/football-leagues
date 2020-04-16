--liquibase formatted sql

--changeset tprincipi:football-leagues-1 Create database tables

CREATE TABLE competition (
    id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    code VARCHAR(10) UNIQUE,
    area_name VARCHAR(25),
    CONSTRAINT pk_competition_id PRIMARY KEY (id)
);

CREATE TABLE team (
    id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE,
    tla VARCHAR(3) UNIQUE,
    short_name VARCHAR(25),
    area_name VARCHAR(25),
    email VARCHAR(50),
    CONSTRAINT pk_team_id PRIMARY KEY (id)
);

CREATE TABLE player (
    id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(50),
    position INTEGER,
    date_of_birth DATE,
    country_of_birth VARCHAR(50),
    nationality VARCHAR(50),
    team_id SMALLINT UNSIGNED NOT NULL,
    CONSTRAINT pk_player_id PRIMARY KEY (id)
);
alter table player add constraint fk_team_team_id foreign key (team_id) references team (id);

CREATE TABLE team_competition (
    id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    team_id SMALLINT UNSIGNED NOT NULL,
    competition_id SMALLINT UNSIGNED NOT NULL,
    CONSTRAINT pk_team_competition_id PRIMARY KEY (id)
);
alter table team_competition add constraint fk_team_competition_id foreign key (team_id) references team (id);
alter table team_competition add constraint fk_competition_team_id foreign key (competition_id) references competition (id);
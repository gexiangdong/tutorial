create database main_db;

create table customer(
    id serial primary key,

    dataSourceClassName varchar(100),
    dataSourceUser varchar(50),
    dataSourcePassword varchar(50),
    dataSourceDataBaseName varchar(50),
    dataSourcePortNumber int,
    dataSourceServerName varchar(50)
);

insert into customer(dataSourceClassName, dataSourceUser, dataSourcePassword, dataSourceDataBaseName, dataSourcePortNumber, dataSourceServerName)
    values('org.postgresql.ds.PGSimpleDataSource', 'pgdbo', '', 'tvseries', 5432, '127.0.0.1');



create database tvseries;

/**    TABLES      **/
create table tv_series(
   id serial primary key,
   name varchar(50) not null,
   season_count int not null,
   origin_release date not null,
   status smallint not null default 0,
   delete_reason varchar(100) null
);

create table tv_character(
    id serial primary key,
    tv_series_id int not null,
    name varchar(50) not null,
    photo varchar(100) null
);

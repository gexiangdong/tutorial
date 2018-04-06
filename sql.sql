create database tvseries;

/**    TABLES (section02-04中使用)     **/
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


/**
 * section-05中测试@Transactional注解用
 */
create table person(
   id int primary key,
   name varchar(50) not null,
   birthday timestamp default now()
);

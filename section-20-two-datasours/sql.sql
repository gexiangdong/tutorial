
/**
 * 测试@Transactional注解用
 */

create database db1;

## 进入db1， 拷贝粘贴时需注意，下面这行要单独执行，和其他一起拷贝执行可能不成功
\c db1

select current_database();

create table person(
   id int primary key,
   name varchar(50) not null,
   attributes json  not null,
   birthday timestamp default now(),
   post_code varchar(10) not null,
   detail_address varchar(100) null
);

insert into person(id, name, attributes, birthday, post_code, detail_address) values (1, 'jack', '{}', '1999/01/01 13:13', '456', 'road 1');
insert into person(id, name, attributes, birthday, post_code, detail_address) values (2, 'tom', '{}', '2000/01/01 13:13', '123', 'road 2');
insert into person(id, name, attributes, birthday, post_code, detail_address) values (3, 'john', '{}', '2011/01/01 13:13', '789', 'road 3');

create database db2;

## 进入db2， 拷贝粘贴时需注意，下面这行要单独执行，和其他一起拷贝执行可能不成功
\c db2

select current_database();

create table hero(
   id int primary key,
   name varchar(50) not null,
   attributes json  not null,
   birthday timestamp default now()
);

insert into hero(id, name, attributes, birthday) values (101, 'Hero A', '{}', '1999/03/01 13:13');
insert into hero(id, name, attributes, birthday) values (102, 'Hero B', '{}', '2000/03/01 13:13');
insert into hero(id, name, attributes, birthday) values (103, 'Hero 3', '{}', '2011/03/01 13:13');


/**
 * 测试@Transactional注解用
 */
create table person(
   id int primary key,
   name varchar(50) not null,
   birthday timestamp default now()
);

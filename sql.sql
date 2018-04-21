create database tvseries;

/** *******************************************************
     TABLES (section02-04中使用)     
    *************************************************** **/
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


/** *******************************************************
     section-05中用到的表和数据   
    *************************************************** **/
create table person(
   id int primary key,
   name varchar(50) not null,
   birthday timestamp default now()
);


/** *******************************************************
     section-06中用到的表及数据 
    *************************************************** **/
create table product(
	id char(5) not null primary key,
	name varchar(20) not null,
	price int not null,
	images varchar(100)[],
	specs JSON null
);

/** 枚举类型的ordertype **/
CREATE TYPE ORDERTYPE AS ENUM ('RETAIL', 'WHOLESALE');

create table order_main(
    id serial primary key,
    order_date timestamp not null,
    consignee varchar(20) not null,
    phone varchar(20) not null,
    province varchar(50) null,
    city varchar(50) null,
    district varchar(50) null,
    address varchar(50) null,
    order_type ORDERTYPE,
    status int default 0
);

create table order_detail(
    id serial primary key,
    order_id int not null,
    product_id varchar(10) not null,
    product_name varchar(100) not null,
    num int not null,
    price int not null
);

insert into product(id, name, price, specs, images) values('E0001', 'iPhone X手机模型', 10000, '{"颜色":"黑色", "材质":"塑料"}', '{"a1.jpg", "a2.jpg", "a3.jpg"}');
insert into product(id, name, price, specs, images) values('B0002', '双肩电脑包', 25000, '{"产地":"中国", "电脑夹层":"有"}', null);

insert into order_main(order_date, consignee, phone, province, city, district, address, order_type, status)
    values('2017/4/28 12:13:14', '郝尤乾', '13300001111', '上海', '上海', '徐汇', '漕宝路22222号', 'RETAIL', 0);
insert into order_detail(order_id, product_id, product_name, num, price)
    values(1, 'E0001', 'iPhone X手机模型', 1, 10000);
insert into order_detail(order_id, product_id, product_name, num, price)
    values(1, 'B0002', '双肩电脑包', 1, 25000);
    

create table product(
	id char(5) not null primary key,
	name varchar(20) not null,
	price int not null,
	specs JSON null,
	images varchar(100) ARRAY null 
);

create table order_main(
    id serial primary key,
    order_date timestamp not null,
    consignee varchar(20) not null,
    phone varchar(20) not null,
    province varchar(50) null,
    city varchar(50) null,
    district varchar(50) null,
    address varchar(50) null,
    order_type int not null,
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

insert into product(id, name, price) values('E0001', 'iPhone X手机模型', 10000);
insert into product(id, name, price) values('B0002', '双肩电脑包', 25000);

insert into order_main(order_date, consignee, phone, province, city, district, address, status)
    values('2017/4/28 12:13:14', '郝尤乾', '13300001111', '上海', '上海', '徐汇', '漕宝路22222号', 0);
insert into order_detail(order_id, product_id, product_name, num, price)
    values(1, 'E0001', 'iPhone X手机模型', 1, 10000);
insert into order_detail(order_id, product_id, product_name, num, price)
    values(1, 'B0002', '双肩电脑包', 1, 25000);
    
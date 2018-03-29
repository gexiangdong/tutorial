README
===========================
    Spring Transactional @Transactional中propagation参数的例子，用实际例子来了解各种情况，比看文档更深刻些，需要PostgreSQL Server。 

### 代码简介
    运行AppTests中的doSomthing的测试用例，doSomthing中有6个函数，建议每次运行1个，以便看得更清楚。

### 准备数据库
    如果没有PostgreSQL Server，请先安装一份。可从http://www.postgres.org下载。
    创建一个数据库，名为tvseries，并在此数据库内创建1个表，建表语句可参考sq.sql(./sql.sql)文件。
````SQL
create table person(
   id int primary key,
   name varchar(50) not null,
   birthday timestamp default now()
);
````

### 运行
```bash
mvn test
```


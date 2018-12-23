动态数据源
===========================

这是一个并**不常见**的需求的实现方案，记录在此为了以后再遇到时查找方便。

## 需求

整个项目中有一个主数据库（肯定存在）和若干可以在主库的某个表内配置的数据库组成。例如：每个客户有一个单独的数据库，客户数据存在客户数据库内。

主数据库：main_db

若干客户数据库，在main_db.customer内记录

每个请求默认使用客户的数据库，请求会在request.header中增加一个customerId来标志是哪个客户。

## 数据库

main_db内有一个表

```sql
create table customer(
    id serial primary key,

    dataSourceClassName varchar(100),
    dataSourceUser varchar(50),
    dataSourcePassword varchar(50),
    dataSourceDataBaseName varchar(50),
    dataSourcePortNumber int,
    dataSourceServerName varchar(50)
)

insert into customer(dataSourceClassName, dataSourceUser, dataSourcePassword, dataSourceDataBaseName, dataSourcePortNumber, dataSourceServerName)
    values('org.postgresql.ds.PGSimpleDataSource', 'pgdbo', '', 'tvseries', 5432, '127.0.0.1');
    
```

上表中登记的tvseries数据库，和其他章节的数据库相同。

## 代码说明

* [DataSourceConfig.java](src/main/java/cn/devmgr/tutorial/DataSourceConfig.java) 实现动态数据源的配置
* [DbFilter.java](src/main/java/cn/devmgr/tutorial/DbFilter.java) 对每个request，获取request header(CustomerId)并记录到ThreadLocal，以便DynamicDataSource根据ThreadLocal内值选择数据源
* [ThreadLocalContext.java](src/main/java/cn/devmgr/tutorial/ThreadLocalContext.java) 用于记录ThreadLocal中变量的统一处理类
* [CustomerService.java](src/main/java/cn/devmgr/tutorial/service/CustomerService.java) 在程序内动态切换数据源的例子


## 测试

使用浏览器直接访问，因为没有传递request header customerId会出现500错误。

使用curl，传递了customerId的header，且值在customer表内存在则不会出错了。

```bash
curl -H "customerId:1" http://localhost:8080/tvseries/
```

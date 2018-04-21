Mybatis进阶
===========================
请注意这节代码中例子场景和其他章节不同。

这节是以下订单为场景进行的，包含产品(Product)、订单（主类Order，子类OrderItem）等元素。
主要演示了以下几种用途：
* 使用resultmap把SQL查询结果影射到一对一的两个类上（Order类中有一个属性是ConsigneeAddress类）
* 使用resultmap做一对多的映射（Order类中包含多个OrderItem类）
* dao中一个方法执行多条insert； (OrderDao.insertOrder)
* EnumTypeHandler、ArrayTypeHandler的用法
* 自定义的通用型JsonTypeHandler

[OrderDao.xml](./src/main/resources/cn/devmgr/tutorial/OrderDao.xml)是本例中最重要的一个文件，上述几个特性大多在这个文件内配置完成。

[JsonTypeHander.java](./src/main/java/cn/devmgr/tutorial/typehandler/JsonTypeHandler.java)是一个通用的JSON转换处理器，可以把数据库的JSON字段转成特定的类型。

### 数据库
这节需要三个新表，需要先创建才能运行本节的代码，创建表的SQL在[db.sql](./db.sql)
表中有使用到PostGreSQL中的数组类型列(product.images）、JSON类型列（product.specs)、枚举类型列(order_main.order_type)

### 运行
```bash
mvn spring-boot:run
```
启动后，可通过http://localhost:8080/orders 来查看结果。


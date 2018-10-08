# Spring Boot教程

spring boot编写RESTful API项目中用到的各种知识的整理和相关示例，主要通过代码和代码的注视来展现说明用法和注意事项。

可以配合网易云免费课程 http://study.163.com/course/courseMain.htm?courseId=1005213034 一起。

****

章节 | 说明 |
|:------------ |:--------------- |
|[seciont-00](./section-00) | 最简单的可运行的RESTful API例子  |
|[seciont-01](./section-01) | RestController的例子，展示了编写RestController中的各种用法  |
|[seciont-02](./section-02) | Spring boot + Mybatis查询存储数据的例子【不区分DTO、PO等，DTO穿透所有层】  |
|[seciont-03](./section-03) | Spring boot + JPA实现数据存取的例子，并且示例了用Zoker处理DTO、PO之间的转换 |
|[seciont-04](./section-04) | 使用Spring boot tester 和 Junit 及mockit进行单元测试 |
|[seciont-05](./section-05) | 测试用例展现@Transactional注解的几个参数的用途和含义 |
|[seciont-06](./section-06) | Mybatis进阶，演示查询结果到复杂类型的转换；演示了TypeHandler及自定义JsonTypeHandler的用途 |
|[seciont-07](./section-07) | 使用Spring Security保护RESTful API |
|[seciont-08](./section-08) | 使用Spring Async、Spring Scheduling、Spring Cache的例子 |
|[seciont-09](./section-09) | 用Spring编写websocket的例子（使用了STOMP) |
|[seciont-10](./section-10) | 在Spring中使用JMS(ActiveMQ)收发消息的例子 |
|[seciont-11](./section-11-mvc) | SpringMVC的例子 |
|[seciont-12](./section-12-mvc-security) | SpringMVC + Spring Security，定制登录页面和自定义用户机制的例子 |
|[seciont-13](./section-13-kafka) |使用kafka作为消息服务器，收发消息的例子 |


### 环境

需要先安装Maven，大部分章节需要PostgreSQL，安装后创建数据库和表[sql.sql](./sql.sql)

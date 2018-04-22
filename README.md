# 用spring boot编写RESTful API教程

spring boot编写RESTful API项目中用到的各种知识的整理和相关示例，主要通过代码和代码的注视来展现说明用法和注意事项。

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


### 环境

需要先安装Maven，大部分章节需要PostgreSQL，安装后创建数据库和表[sql.sql](./sql.sql)

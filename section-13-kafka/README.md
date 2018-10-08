Kafaka
===========================

使用spring连接kafka收发消息的简单用法


#### 安装kafka

请参考 https://kafka.apache.org/quickstart

*注意*：kafka服务的运行需要依赖zookeeper服务，如果zookeeper服务停掉，kafka服务无法正常运行。

### 运行
```bash
mvn spring-boot:run
```

### 代码说明

#### 配置kafka服务

application.yml中增加
```yml
spring:
  kafka:
    bootstrap-servers: localhost:9092 #kafka服务器IP和端口
    consumer:
      group-id: my-group  #默认的消费者groupid
```
如果消息需要多个消费者接收，每个消费者的consumer.group-id应该不同，同一个group内只有有一个收到消息

#### 发送消息

使用KafkaTemplate发送消息，请参考[SampleService.java](src/main/java/cn/devmgr/tutorial/SampleService.java)


#### 接收消息

使用@KafkaListener注解，请参考[SampleController.java](src/main/java/cn/devmgr/tutorial/SampleController.java)


## kafka vs. ActiveMQ
简单的说，kafka的性能比ActiveMQ好，但功能不如ActiveMQ多，对JMS支持也不如ActiveMQ好。 企业内应用一般ActiveMQ；互联网应用一般Kafaka。

项目 | ActiveMQ | Kafka |
|:------------:|:------------:|:------------:|
|单机吞吐量 | 较差 | 优 |
|持久化能力 | 内存、文件、数据库 | 磁盘文件 |
|协议支持 |OpenWire、STOMP、REST、XMPP、AMQP | 自有协议，社区封装HTTP支持 |
|事务| 支持 |不支持|
|管理界面|有|仅命令行|
|消息推拉模式| Pull/Push均支持 | Pull |
|客户端支持的语言|Java, C, C++, Python, PHP, Perl, .net等 |官方支持java，开源社区有增加其他语言|



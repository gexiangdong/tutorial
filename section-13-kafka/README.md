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

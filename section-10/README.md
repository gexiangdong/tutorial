README
===========================

演示**Spring JMS**的用法

### 环境配置

本例子需要使用ActiveMQ，ActiveMQ支持window, Linux，可在http://activemq.apache.org/download.html 下载获得。

ubuntu下也可使用apt-get安装

```Bash
apt-get install activemq
```


### 运行
```bash
mvn spring-boot:run
```
启动后，可以在主控台看到SampleService.receiveMessage()方法接收到消息的日志。
这些消息有2个来源：
* SampleService.sendMessage() 中定时发送的消息（每10秒一次）
* 访问http://localhost:8080/ 后给activeMQ发送的消息

也可以打开activeMQ的web控制台，查看消息队列的消息统计等。

### 代码说明
首先要在启动类内通过@EnableJMS打开JMS功能。然后在[applicaiton.yml](src/main/resources/application.yml)中配置ActiveMQ的broker-url参数。

需要发送消息的地方，要装载JmsTemplate构件，然后调用这个构件发送消息。需要接收某个队列的消息可以直接在方法上使用@JmsListener注解，当队列里有消息时会调用被注解的方法。这些都可参照[SampleService.java](src/main/java/cn/devmgr/tutorial/SampleService.java)中相关代码。

spring boot项目中，消息是点对点模式(PTP)还是发布订阅模式(PUB/SUB)可以在application.yml中通过 spring.jms.pub-sub-domain 来设置，false表示PTP，true表示PUB/SUB，默认是PTP，没法简单的同时使用两种模式。



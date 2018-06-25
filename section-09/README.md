README
===========================

演示**Spring WebSocket**的用法

### 运行
```bash
mvn spring-boot:run
```
启动后，可通过http://localhost:8080/index.html?000 来查看结果。

### 代码说明

#### 客户端
[index.html](src/main/resources/public/index.html)是用来测试连接服务端的html+js，放在resources/public目录下的html文件会自动被spring boot映射出来访问。

index.html文件中有连接网上的scoket.js和stomp.js文件及bootstrap.css，测试时需要浏览器能够正常访问相应的网站获取资源。

访问时通过http://localhost:8080/index.html?xxx后面增加不同的querystring来表示不同的用户。

#### 服务端
[WebSocketConfig.java](src/main/java/cn/devmgr/tutorial/WebSocketConfig.java) websocket的配置类，包含了身份识别等。

[SocketController.java](src/main/java/cn/devmgr/tutorial/SocketController.java) 用来接收客户端发送到服务器端的消息，并发送到其他连接的客户端。注意这个类上有@Controller注解，方法上有@CMessageMapping注解来设定用于接收客户端消息的地址。

[ScheduledTasks.java](src/main/java/cn/devmgr/tutorial/ScheduledTasks.java) 用来定时向各个连接到socket上的客户端发消息（当前时间）。
* reportCurrentTime()方法是给所有人发送消息的例子；
* reportCurrentTimeTo000()是单独发送消息（给用户000发送消息）的例子，单独发送消息是通过用户Principal对象识别的，每个连接对应的Principal对象在WebSocketConfig中有设置。
1 要在浏览器中查看reportCurrentTimeTo000()发送的消息，可以用http://localhost:8080/index.html?000 就会看到(JS把querystring中字符串当成token传递给服务端，服务端WebSocketConfig中把token作为Principal中的userName，sendToUser按照Principal中的userName发送）


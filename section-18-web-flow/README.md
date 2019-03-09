# Web Flow with SpringMVC

Spring Web Flow 是在 SpringMVC 基础上用于做按照一定步骤的形式的一组页面。



本例子用是一个简单的查看购物车、确认订单、创建订单、支付的流程。


## 步骤

### pom

```xml
<dependency>
    <groupId>org.springframework.webflow</groupId>
    <artifactId>spring-webflow</artifactId>
    <version>2.5.0.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.webflow</groupId>
    <artifactId>org.springframework.js</artifactId>
    <version>2.0.5.RELEASE</version>
</dependency>
```

webflow 需要SpringMVC，所以pom里应该有spring mvc和对应的模板(thymeleaf)的依赖。

### 创建配置类

[参照WebFlowConfig.java](./src/main/java/cn/devmgr/tutorial/webflow/WebFlowConfig.java)


### 创建流程配置的xml

[参照buy-flow.xml](./src/main/resources/flows/buy/buy-flow.xml)

此类中可以使用spring的compent，执行里面的方法等，用springEL方式调用。

### 创建流程中使用的模版

[参数templates/flows/buy目录](./src/main/resources/templates/flows/buy)

## ERROR

所有的在流程里传递的POJO都需要实现 Serializable 接口，否则运行时出现异常。
注意还要包含他们的成员变量的类、内部类等。

    Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.webflow.execution.repository.snapshot.SnapshotCreationException: Could not serialize flow execution; make sure all objects stored in flow or flash scope are serializable] with root cause
    java.io.NotSerializableException: cn.devmgr.tutorial.webflow.pojo.ShoppingCart
    
    

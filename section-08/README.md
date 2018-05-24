README
===========================

演示**Spring Cache**、**Spring Async**、**Spring Schedule**的用法

### 运行
```bash
mvn spring-boot:run
```

### 代码说明

为了启用Schedul, Async, Cache，需要在@Configuration类或@SpringBootApplication类中用注解@EnableScheduling @EnableAsync @EnableCaching打开相应的注解支持。

#### Scheduling 注解
[ScheduledTasks.java](src/main/java/cn/devmgr/tutorial/ScheduledTasks.java) 内有Scheduling注解的例子。

[SchedulingConfigurerConfiguration.java](src/main/java/cn/devmgr/tutorial/SchedulingConfigurerConfiguration.java) 是用来配置有多少个线程来运行Scheduled Tasks，不配置默认是1个线程。如果几个scheduled task需要同时执行就需要更多的线程了。

#### Async 注解
[SampleService.java](src/main/java/cn/devmgr/tutorial/SampleService.java)内有async相关注解的例子。

#### Cache 注解
[SampleController.java](src/main/java/cn/devmgr/tutorial/SampleController.java)内有cache相关注解的例子。

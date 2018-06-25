Async、Scheduling和Cache
===========================

演示**Spring Cache**、**Spring Async**、**Spring Scheduling**的用法

### 环境

测试redis部分需要在本机安装redis，端口6379，无密码；如果环境不符合这些配置，可以修改application.yml改成和环境相符的配置。

如果没有redis环境，想仅仅测试下spring用内存做缓存，可以修改pom.xml文件去掉如下依赖：
```XML
   <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
```
和[SampleController.java](src/main/java/cn/devmgr/tutorial/SampleController.java)中RedisTemplate相关部分。

#### 安装Redis

ubuntu
```Bash
apt-get install redis
```
Mac OS
```Bash
brew install redis
```
Windows，Redis官方并不支持windows，建议安装一个linux的虚拟机，做后端开发服务器一般都是用linux。


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

#### Cache 注解和 RedisTemplate
[SampleController.java](src/main/java/cn/devmgr/tutorial/SampleController.java)内有cache相关注解的例子。

# SpringMVC的例子

## 运行
```Bash
mvn spring-boot:run
```

## 代码说明
* Model: 在spring mvc，Model是在controller里设置的，所以需要看controller中代码
* View：本例中的view都在 src/main/resources/templates 目录下
* Controller: 在本例中，有两个Controller，在src/main/java/cn/devmgr/tutorial/springmvc目录下



### i18n
src/main/resources/Messages.properties 文件是模版中使用的多语言字符串文件，如果需要对应多语言，可创建Messages_zh_CN.properties, Messages_en_US.properties等文件分别存储需要的语言的字符。


### 配置

#### application.yml
下面这段是用来配置不缓存模版的，在开发过程中特别有用，但在生产环境下最好设置成true，使用缓存速度会快点。
开发环境下不用cache，是因为改了模版刷新下浏览器就看到结果了。
```yml
spring:
  thymeleaf:
    cache: false
```


#### POM
spring-boot-starter-web是必须的：
```XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

如果使用thymeleaf作为模版引擎，则需要spring-boot-starter-thymeleaf依赖
```XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

为了使用thymeleaf的layout功能，需要下面这个依赖：
```XML
<dependency>
    <groupId> nz.net.ultraq.thymeleaf</groupId>
    <artifactId>thymeleaf-layout-dialect</artifactId>
</dependency>
```

# SpringMVC的例子

## 运行
```Bash
mvn spring-boot:run
```

## 代码说明
* Model: 在spring mvc，Model是在controller里设置的，所以需要看controller中代码
* View：本例中的view都在 src/main/resources/templates 目录下
* Controller: 在本例中，有两个Controller，在src/main/java/cn/devmgr/tutorial/springmvc目录下

### 静态内容
src/main/resources/static 目录下的文件可以直接被浏览器访问到，可以放置css、js、图片等，在生产环境上，这些文件放置到nginx等可以直接访问的目录下更好，nginx/apache等处理静态内容的速度要好过tomcat。访问量大的网站甚至可以单独把这些静态内容部署到CDN上。

[关于静态资源的更多信息可参照这里](https://github.com/gexiangdong/tutorial/wiki/静态资源)。

### i18n
[Messages.properties](./src/main/resources/Messages.properties) 文件是模版中使用的多语言字符串文件，如果需要对应多语言，可创建Messages_zh_CN.properties, Messages_en_US.properties等文件分别存储需要的语言的字符。

在模版中可以通过th:text="#{xxx}" (xxx是Message.properites中的key）来显示，spring会自动找到对应语言的字符串。
```HTML
<p th:text="#{welcome.text}">Welcome to our store!</p>
```

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



### layout

layout不是SpringMVC必须的，使用fragment功能也可以实现界面模板的共用。layout提供了更好的公共界面部分的分离，便于后续修改维护。

使用layout功能需要以下几步：

1. 增加pom依赖 thymeleaf-layout-dialect 
   ```xml
    <dependency>
        <groupId>nz.net.ultraq.thymeleaf</groupId>
        <artifactId>thymeleaf-layout-dialect</artifactId>
    </dependency>
   ```    
1. 创建layout，layout也是普通的模板文件，需要在页面上替换的部分增加属性 ` layout:fragment="content" `, content 是名字，根据需要起个合适的名字
1. 在模板中使用layout，在html标签上增加 ` xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" ` 和 ` layout:decorator="layout/default" `， decoration属性指定这个页面使用的模板文件在那里，从templates目录下开始的路径，不需要增加 .html 的扩展名
1. 在模板文件中通过给元素增加属性 ` layout:fragment="content" ` 来指定模板中需要被替换掉的部分。

### 注释

模板文件里增加的一些注释，我们并不希望输出到最终渲染的html里，不希望它们被最终用户通过查看源文件的形式看到。可以通过在 ` <!--/* 注释写这 */-->`来实现，这个也支持多行，例如：

 ```html
<!--/*
多行注释
这些注释不会springmvc输出到客户端
这个注释很容易记，就是html的注释+java的多行注释
*/-->

```



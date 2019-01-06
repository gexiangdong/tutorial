README
===========================

用Spring中提供的JavaMailSender发送邮件的例子


## 加入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```


## 配置application.yml

application.yml中需要配置发送邮件的SMTP的信息

```yml
spring:
  mail:
    host: smtp主机地址，例如网易域名邮箱是：smtp.ym.163.com；qq邮箱是smtp.qq.com
    username: smtp登录的账号，一般是邮件地址
    password: smtp登录账号对应的密码，一般和自己邮箱密码相同
    properties:
      mail:
        smtp:
          auth: true
```

## 写代码发送

可参考[MailService](.src/main/java/cn/devmgr/tutorial/MailService.java)

此法相比直接调用javamail发送邮件，稍微简单些。


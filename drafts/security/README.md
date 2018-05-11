在pom.xml中加入spring-security，只需加入spring-boot-starter-security包即可，spring boot会自动引入spring-security等需要的依赖。
```XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
加入spring security后，如果不做任何配置，spring boot会启动默认设置，创建一个in-memory store保存UserDetailService，并创建一个名为user，密码自动生成的用户，生成的密码会在主控台上显示出来，例如
```
Using generated security password: ab58a84f-a2a8-475b-8cae-b3c992f5a1a8

```



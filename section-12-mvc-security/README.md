# SpringMVC + Spring Security的例子

## 代码说明
使用spring security保护SpringMVC程序的例子，演示了如何配置spring security，自定义登录页面和用户。

* [User](./src/main/java/cn/devmgr/tutorial/mvc/User.java)类是登录用户的类
* [UserService](./src/main/java/cn/devmgr/tutorial/mvc/UserService.java) 用于查询某个用户，实际使用中一般从数据库内查询，此例没有使用数据库
* [WebSecurityConfig](./src/main/java/cn/devmgr/tutorial/mvc/WebSecurityConfig.java) 用来配置Spring Security
* [MvcConfig](./src/main/java/cn/devmgr/tutorial/mvc/MvcCOnfig.java) 配置自定义登录页面模版
* [login.html](./src/main/resources/templates/login.html) 是登录页面模版
* [SampleController](./src/main/java/cn/devmgr/tutorial/mvc/SampleController.java) 是一个简单的例子

## 启动
```Bash
mvn spring-boot:run
```
## 测试：
http://localhost:8080/all 会自动跳转到登录界面，登录成功后会返回


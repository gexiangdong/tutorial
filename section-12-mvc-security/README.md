# SpringMVC + Spring Security的例子

## 代码说明
使用spring security保护SpringMVC程序的例子，演示了如何配置spring security，自定义登录页面和用户。

* User类是登录用户的类
* UserService 用于查询某个用户，实际使用中一般从数据库内查询，此例没有使用数据库
* WebSecurityConfig 用来配置Spring Security
* MvcConfig 配置自定义登录页面模版
* login.html 是登录页面模版
* SampleController 是一个简单的例子

## 启动
```Bash
mvn spring-boot:run
```
## 测试：
http://localhost:8080/all 会自动跳转到登录界面，登录成功后会返回


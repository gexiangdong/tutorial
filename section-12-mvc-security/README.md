# SpringMVC + Spring Security的例子

使用spring security保护SpringMVC程序的例子，演示了如何配置spring security，自定义登录页面和用户。

此例中没有使用Spring Security注解的例子，Spring Security注解的使用可以参照[section-07](https://github.com/gexiangdong/tutorial/tree/master/section-07)


## 代码说明

* [User](./src/main/java/cn/devmgr/tutorial/mvc/User.java)类是登录用户的类
* [UserService](./src/main/java/cn/devmgr/tutorial/mvc/UserService.java) 实现SpringSecurity的UserDetailsService，和spring security框架配合，查询某个用户。实际使用中一般从数据库内查询，此例没有使用数据库
* [WebSecurityConfig](./src/main/java/cn/devmgr/tutorial/mvc/WebSecurityConfig.java) 用来配置Spring Security
* [MvcConfig](./src/main/java/cn/devmgr/tutorial/mvc/MvcConfig.java) 配置自定义登录页面模版
* [login.html](./src/main/resources/templates/login.html) 是登录页面模版
* [SampleController](./src/main/java/cn/devmgr/tutorial/mvc/SampleController.java) 是一个简单的例子

## 启动

```Bash
mvn spring-boot:run
```

## 测试：

http://localhost:8080/ 可访问；如果访问/user或/admin则会跳转到登录页面；如果登录后仍没有权限访问，则403。

* 用户名可以随意填写，密码都是password，密码填写其他字符会出现用户名或密码错的提示
* 只要登录的用户都会被赋予 "USER" 的角色，如果登录名是admin，则还会被赋予 "ADMIN" 的角色
* /user，"USER" 角色即可访问
* /admin 只有 "ADMIN"角色才可访问 
* 上述的访问限制是在[WebSecurityConfig](./src/main/java/cn/devmgr/tutorial/mvc/WebSecurityConfig.java) 中设定的，也可通过注解在Controller里设置，参见[section-07](../section-07)，此节不再举例
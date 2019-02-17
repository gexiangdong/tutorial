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

## CSRF保护

使用spring security时，默认是打开CSRF保护，这是为了避免CSRF攻击。开启CSRF防护后，普通的POST方法会返回403，需要在表单中增加csrf token，来避免csrf攻击。

```html
 <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>
```
可以参照[form.html](./src/main/resources/templates/form.html) 。

CSRF TOKEN是spring放入session中的一个字串，再提交时spring security会比较客户端提交的token和session里存储的是否相同，不同则认为正在遭受csrf攻击，显示403，拒绝处理请求。也可以在Java程序中获取这个字串：

```Java
CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
// token.getHeaderName() 用request header验证时的header name（一般用于AJAX调用）
// token.getParameterName() 用post form时的提交的参数名 
// token.getToken() token的字串
```

## 在thymeleaf模版中针对不同角色/权限的用户显示不同的内容

### 增加依赖

```xml
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity4</artifactId>
</dependency>

```

### 在模版里可以使用sec:authorize属性，当值为true时可见

```html
<div sec:authorize="hasRole('USER')">具有USER角色的用户可见。</div>
<div sec:authorize="hasRole('ADMIN')">具有USER角色的用户可见。</div>

<div sec:authorize="hasAuthority('edit')">具有edit权限的用户可见。</div>
<div sec:authorize="hasAuthority('delete')">具有delete权限的用户可见。</div>

<div sec:authorize="isAuthenticated()">
  所有登录用户均可见
</div>

```

可以在html标签上定义sec的xmlns，以避免IDE提示错误

```html
<html xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
```

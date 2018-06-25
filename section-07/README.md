README
===========================
Spring Security的例子

### 代码简介
展示了用spring security保护RESTful API的几种方式。 为了搭建环境需要以下几点：
* pom文件中加入依赖
```XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
* 增加一个WebSecurityConfigurerAdapter，详细代码参见 [WebSecurityConfigurerAdapter.java](./src/main/java/cn/devmgr/tutorial/WebSecurityConfigurerAdapter.java)
* 增加一个Filter用于把token转换成用户[AuthenticationTokenFilter.java](./src/main/java/cn/devmgr/tutorial/AuthenticationTokenFilter.java)
* 登录用的controller [TokenController.java](./src/main/java/cn/devmgr/tutorial/TokenController.java)

### 运行
```bash
mvn spring-boot:run
```

### 代码提供的API列表

#### 登录
HTTP Action: POST /token <br>
对应的方法： TokenController.login() <br>
```Bash
curl -H "Content-Type:application/json" -X POST --data '{"username":"author", "password":"password"}' http://localhost:8080/token

```

如果用户名和密码都正确，会返回token，token可以在后续接口中使用，本例中有三个用户

用户名 | 密码 | 对应的角色 |
|:------------ |:--------------- |:--------------- |
|author | password | author |
|reader | password | reader |
|admin  | password | author reader |


#### 退出登录
HTTP Action: DELETE /token <br>
对应的方法： TokenController.logout() <br>
```Bash
curl -H "Authorization: Bearer xxxx" -X DELETE http://localhost:8080/token

```
xxxx需要替换成登录时返回的token，以下相同，不再单独说明.


#### 使用PreAuthorize注解保护方法
HTTP Action: GET / <br>
对应的方法： SampleController.getAll() <br>
测试方法：<br>
下面这个会返回403，禁止访问状态
```Bash
curl http://localhost:8080/
````

下面携带了token的访问则会正常返回数据，一个长度为2的JSON数组
```Bash
curl  -H "Authorization: Bearer xxxx" http://localhost:8080/
````

#### 在方法中获取当前用户
HTTP Action: GET /{id} <br>
对应的方法： SampleController.getOne(int id) <br>
测试方法：<br>
下面这个测试没有携带用户的token，会返回一个JSON，内有2个属性
```Bash
curl http://localhost:8080/101
```
下面这个测试使用了token，会返回一个JSON，内有3个属性，其中一个是当前用户名
```Bash
curl -H "Authorization: Bearer xxxx" http://localhost:8080/101
```


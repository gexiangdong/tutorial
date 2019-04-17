README
===========================
针对section-02章节的代码，编写的测试用例，测试用例使用了junit spring-boot-starter-test和mockit。测试用例在src/test/java目录下，测试用例内有对应的注释说明。
本章节以section-02为基础，因此需要现有可以运行section-02的环境。


### 准备数据库
如果没有PostgreSQL Server，请先安装一份。可从[http://www.postgres.org](http://www.postgres.org)下载。
创建一个数据库，名为tvseries，并在此数据库内创建2个表，建表语句可参考[sql.sql](../sql.sql)文件。
修改[application.yml](./src/main/resources/application.yml)中的数据库连接字符串

### 运行
```bash
mvn test
```
### 几个测试用例的说明

| 所在类 | 方法 | 说明 |
|:-----:|:----:|:-----|
|[TvSeriesServiceTest](./src/test/java/cn/devmgr/tutorial/TvSeriesServiceTest.java) | testGetAllWithoutMockit | 没有使用mockit来做桩模块替代掉数据访问层时，判断测试是否成功的条件比较难编写，这是个负面例子。 |
|[TvSeriesServiceTest](./src/test/java/cn/devmgr/tutorial/TvSeriesServiceTest.java) | testGetAll |  |
|[TvSeriesServiceTest](./src/test/java/cn/devmgr/tutorial/TvSeriesServiceTest.java) | testGetOne |  |
|[AppTests](./src/test/java/cn/devmgr/tutorial/AppTests.java) | contextLoads |一个空测试用例  |
|[AppTests](./src/test/java/cn/devmgr/tutorial/AppTests.java)  | testGetAll |GET方法的测试，数据访问层被桩模块代替  |
|[AppTests](./src/test/java/cn/devmgr/tutorial/AppTests.java)  | testAddSeries |POST方法的测试，数据访问层被桩模块代替  |
|[AppTests](./src/test/java/cn/devmgr/tutorial/AppTests.java)  | testFileUpload |MockWebMvc模拟文件上传，来测试文件上传的例子，内有如何修改某个spring bean属性的语句。 |


## 代码说明

### pom 中的依赖

要进行单元测试，junit是必须的，如果被测部分涉及到spring的内容，还需要spring-boot-starter-test, 为了隔绝底层对上层的影响，此例子使用了 mockito 来制作测试的壮模块。

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```
### testcase

测试用例里如果只测试services，不需要启动spring boot的web服务，则在测试用例类上增加这2个注解就够了：

```java
@RunWith(SpringRunner.class)
@SpringBootTest
```

如果需要启动spring boot的web服务来测试，则还需要再增加一个注解 `@AutoConfigureMockMvc `，

```java
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
```
增加这个注解后会启动spring boot 的 web服务，可以通过http调用来测试，把spring boot对controller的自动调用和返回结果的转换也加入到测试中去。




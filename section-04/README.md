README
===========================
针对section-02章节的代码，编写的测试用例，测试用例使用了junit spring-boot-starter-test和mockit。测试用例在src/test/java目录下，测试用例内有对应的注释说明。
本章节以section-02为基础，因此需要现有可以运行section-02的环境。


### 准备数据库
如果没有PostgreSQL Server，请先安装一份。可从[http://www.postgres.org](http://www.postgres.org)下载。
创建一个数据库，名为tvseries，并在此数据库内创建2个表，建表语句可参考[sql.sql](../sql.sql)文件。
修改application(./src/main/resources/applicaiton.yml)中的数据库连接字符串

### 运行
```bash
mvn test
```
### 几个测试用例的说明

| 所在类 | 方法 | 说明 |
|:-----:|:----:|:-----|
|TvSeriesServiceTest | testGetAllWithoutMockit | 没有使用mockit来做桩模块替代掉数据访问层时，判断测试是否成功的条件比较难编写，这是个负面例子。 |
|TvSeriesServiceTest | testGetAll |  |
|TvSeriesServiceTest | testGetOne |  |
|AppTests | contextLoads |一个空测试用例  |
|AppTests | testGetAll |GET方法的测试，数据访问层被桩模块代替  |
|AppTests | testAddSeries |POST方法的测试，数据访问层被桩模块代替  |
|AppTests | testFileUpload |MockWebMvc模拟文件上传，来测试文件上传的例子，内有如何修改某个spring bean属性的语句。 |

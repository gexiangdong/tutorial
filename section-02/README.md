README
===========================
Spring boot + Mybatis的例子，运行需要PostgreSQL Server。 

### 代码简介
有两个dao类，都在cn.devmgr.tutorial.springboot.dao包内，对应的在src/main/resource/cn/devmgr/tutorial/springboot/dao目录下有2个XML文件。
dao类中方法用到的SQL，简单的有用注解写在接口内，稍微长点的写在了XML内。
TvSeriesService是业务逻辑处理类。属于业务逻辑层。 TvSeriesController是web层控制器，接收请求并处理。
为了清晰的区分出各个类所属的层，每层放在了不同的package内，controller是web控制层，service是业务逻辑层，dao是数据访问层，pojo内放置的类是dto po，此例中没有区分dto和po，几个层级共用同一套pojo。

### 准备数据库
如果没有PostgreSQL Server，请先安装一份。可从http://www.postgres.org下载。
创建一个数据库，名为tvseries，并在此数据库内创建2个表，建表语句可参考sq.sql(./sql.sql)文件。
修改application(./src/main/resources/applicaiton.yml)中的数据库连接字符串

### 运行
```bash
mvn spring-boot:run
```

### 代码提供的API列表

#### 所有电视剧列表API
HTTP Action: GET /tvseries <br>
对应的方法： TvSeriesController.getAll() <br>
测试方法：
```Bash
curl http://localhost:8080/tvseries
````

#### 根据编号查询某个电视剧详情
HTTP Action: GET /tvseries/{id} <br>
对应的方法： TvSeriesController.getOne(int id) <br>
测试方法：
```Bash
curl http://localhost:8080/tvseries/101
curl http://localhost:8080/tvseries/404
````

#### 创建一个新电视剧
HTTP Action: POST /tvseries <br>
对应的方法： TvSeriesController.insertOne(TvSeriesVo tvSeriesVo) <br>
测试方法：
```Bash
curl -H "Content-Type:application/json" -X POST --data '{"name":"West World", "seasonCount":1, "originRelease":"2016-10-02", "tvCharacters":[{"name":"Waytt"},{"name":"Dolores"}]}' http://localhost:8080/tvseries
````

#### 更新一个电视剧的信息
HTTP Action: PUT /tvseries/{id} <br>
对应的方法： TvSeriesController.updateOne(int id, TvSeriesVo tvSeriesVo) <br>
测试方法：
```Bash
curl -H "Content-Type:application/json" -X PUT --data '{"name":"West World", "seasonCount":1, "originRelease":"2016-10-03"}' http://localhost:8080/tvseries/101
```

#### 根据编号查询某个电视剧详情
HTTP Action: DELETE /tvseries/{id} <br>
对应的方法： TvSeriesController.deleteOne(int id, HttpServletRequest request) <br>
测试方法：
```Bash
curl -X DELETE http://localhost:8080/tvseries/101?delete_reason=duplicated
curl -X DELETE http://localhost:8080/tvseries/101
```

#### 给某个电视剧上传一个剧照
HTTP Action: POST /tvseries/{id}/photos <br>
对应的方法： TvSeriesController.addPhoto(int id, MultipartFile imgFile) <br>
测试方法(当前目录下有img.jpg文件）：
```Bash
curl -F "photo=@img.jpg" http://localhost:8080/tvseries/101/photos
```

#### 查看某个电视剧的图标
HTTP Action: GET /tvseries/{id}/icon <br>
对应的方法： TvSeriesController.getIcon(int id) <br>
测试方法：
```Bash
curl -X GET http://localhost:8080/tvseries/101/icon
```

README
===========================
    Spring boot + Spring Data JPA的例子，运行需要PostgreSQL Server。功能和section-02完全一致，实现有些区别。

实现方式 | section-02 | section-03 |
| :----: |:--------- |:--------- |
|DAO层   |Mybatis    |JPA  |
|包划分方法   |Package By Feature <br>大部分类都在同一个包下 |Package By Layer <br>会看到service, controller, dao, po, dto等多个包，每个包内类很少 |
|参数传递 |PO在控制层、逻辑层、数据访问层之间传递 |控制层和逻辑层通过DTO传递参数，逻辑层和数据结构层通过PO传递值，因此在逻辑层多了DTO和PO之间的转换，转换类在cn.devmgr.tutorial.springboot.service.BeanConverter内实现 |

### 代码简介
    有两个dao类，都在cn.devmgr.tutorial.springboot.dao包内，因为继承了JpaRepository，一些基本的增删改查被自动实现。
    每个dao类都有一个自定义的方法，TvSeriesDao.logicDelete实现由Query注解对应的SQL负责；TvCharacterDao.getAllByTvSeriesId则是根据spring jpa的方法规则，由spring jpa猜测出来，根据tvSeriesId查询。
    TvSeriesService是业务逻辑处理类。属于业务逻辑层。 TvSeriesController是web层控制器，接收请求并处理。
    此例子涉及到类较少，采用Package by Feature not by Layer理念分package，所以controller, service, bo等都在同一package内，未区分。

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
HTTP Action: GET /tvseries
对应的方法： TvSeriesController.getAll()
测试方法：
```Bash
curl http://localhost:8080/tvseries
````

#### 根据编号查询某个电视剧详情
HTTP Action: GET /tvseries/{id}
对应的方法： TvSeriesController.getOne(int id)
测试方法：
```Bash
curl http://localhost:8080/tvseries/101
curl http://localhost:8080/tvseries/404
````

#### 创建一个新电视剧
HTTP Action: POST /tvseries
对应的方法： TvSeriesController.insertOne(TvSeriesVo tvSeriesVo)
测试方法：
```Bash
curl -H "Content-Type:application/json" -X POST --data '{"name":"West World", "episodeCount":1, "originalRelease":"2016-10-02", "tvCharacters":[{"name":"Waytt"},{"name":"Dolores"}]}' http://localhost:8080/tvseries
````

#### 更新一个电视剧的信息
HTTP Action: PUT /tvseries/{id}
对应的方法： TvSeriesController.updateOne(int id, TvSeriesVo tvSeriesVo)
测试方法：
```Bash
curl -H "Content-Type:application/json" -X PUT --data '{"name":"West World", "episodeCount":1, "originalRelease":"2016-10-03"}' http://localhost:8080/tvseries/101
```

#### 根据编号查询某个电视剧详情
HTTP Action: DELETE /tvseries/{id}
对应的方法： TvSeriesController.deleteOne(int id, HttpServletRequest request)
测试方法：
```Bash
curl -X DELETE http://localhost:8080/tvseries/101?delete_reason=duplicated
curl -X DELETE http://localhost:8080/tvseries/101
```

#### 给某个电视剧上传一个剧照
HTTP Action: POST /tvseries/{id}/photos
对应的方法： TvSeriesController.addPhoto(int id, MultipartFile imgFile)
测试方法(当前目录下有img.jpg文件）：
```Bash
curl -F "photo=@img.jpg" http://localhost:8080/tvseries/101/photos
```

#### 查看某个电视剧的图标
HTTP Action: GET /tvseries/{id}/icon
对应的方法： TvSeriesController.getIcon(int id)
测试方法：
```Bash
curl -X GET http://localhost:8080/tvseries/101/icon
```

README
===========================
这是一个基本的spring boot项目的例子，包含一个RestController，有7个方法。分别展示了GET、POST、PUT、DELETE等操作方法，HttpServletReqeust、请求中的参数、路径参数获取方式。以及文件上传和返回图片等的方法。

在TvSeriesController的insertOne(@Valid @RequestBody TvSeriesDto tvSeriesDto)方法中演示了使用Bean Validation进行参数校验的例子。具体校验注解的用法参考此方法上的@Valid和TvSeriesDto类属性上的注解以及TvCharacterDto类属性上的注解。


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
下面这个测试应该返回400，因为传递的JSON通不过校验，缺少tvCharacters属性
```Bash
curl -H "Content-Type:application/json" -X POST --data '{"name":"西部世界", "seasonCount":1, "originRelease":"2016-10-02"}' http://localhost:8080/tvseries
```
下面这个调用应该正常返回
```Bash
curl -H "Content-Type:application/json" -X POST --data '{"name":"西部世界", "seasonCount":1, "originRelease":"2016-10-02", "tvCharacters":[{"name":"朵拉瑞斯"}, {"name": "怀亚特"}]}' http://localhost:8080/tvseries
```

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
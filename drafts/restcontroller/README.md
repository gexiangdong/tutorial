README
===========================

最简单的一个可运行的RESTful API

### 运行
```bash
mvn spring-boot:run
```
启动后，可通过http://localhost:8080/tvseries 来查看结果。

## RequestMapping的produces参数
produces参数指明生产出的内容形式，spring会去和request头的Accept部分比较，如果发现相符合，则把方法返回值转换成相符合的格式（例如json, xml等），如果没有符合的，则返回406

RestController的方法上这么写
```Java
@PostMapping(consumes = {MediaType.TEXT_XML_VALUE}, produces = {MediaType.TEXT_XML_VALUE})
```
用下面的命令测试没问题，因为Accept和上面的相符合，会返回xml
```bash
curl -v -d '<TvSeriesDto><id>1</id><name>West Wrold</name><originRelease>2016-10-02</originRelease></TvSeriesDto>' -X POST -H 'Content-type:text/xml' -H 'Accept:text/xml' http://localhost:8080/tvseries
```
如果用下面的命令，则会返回406，  Could not find acceptable representation。因为accept不和方法注解相符合
```bash
curl -v -d '<TvSeriesDto><id>1</id><name>West Wrold</name><originRelease>2016-10-02</originRelease></TvSeriesDto>' -X POST -H 'Content-type:text/xml' -H 'Accept:application/xml' http://localhost:8080/tvseries
```

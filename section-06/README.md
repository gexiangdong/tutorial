Mybatis进阶
===========================
请注意这节代码中例子场景和其他章节不同。

这节是以下订单为场景进行的，包含产品(Product)、订单（主类Order，子类OrderItem）等元素。

主要演示了以下几种方式的技术实现：
* [使用resultmap把SQL查询结果影射到一对一的两个类上](#f1)（Order类中有一个属性是ConsigneeAddress类）
* [使用resultmap做一对多的映射](#f2)（Order类中包含多个OrderItem类）
* [dao中一个方法执行多条insert](#f3) (OrderDao.insertOrder)
* [EnumTypeHandler](#f4)、ArrayTypeHandler的](#f5)用法
* [自定义的通用型JsonTypeHandler](#f6)
* [在映射xml文件中使用include](#f7)

[OrderDao.xml](./src/main/resources/cn/devmgr/tutorial/OrderDao.xml)是本例中最重要的一个文件，上述几个特性大多在这个文件内配置完成。

[JsonTypeHander.java](./src/main/java/cn/devmgr/tutorial/typehandler/JsonTypeHandler.java)是一个通用的JSON转换处理器，可以把数据库的JSON字段转成特定的类型。

### 数据库
这节需要三个新表，需要先创建才能运行本节的代码，创建表的SQL在[db.sql](./db.sql)
表中有使用到PostGreSQL中的数组类型列(product.images）、JSON类型列（product.specs)、枚举类型列(order_main.order_type)

### 运行
```bash
mvn spring-boot:run
```
启动后，可通过http://localhost:8080/orders 来查看结果。


### 复杂类型映射说明

<h4 id="f1">复杂类型结果关联</h4>
此项目中订单类有一个属性是收货地址，收获地址是一个自定义类，两者的关系是一对一，两个类的所有属性都被存储在order_main表中，通过配置resultMap，mybatis自动把查询结果映射到了订单类，并且给订单类正确的创建了收获地址的属性，这一映射通过association实现结果集的关联。

下面是resultMap的示例，为了清晰便于理解，已经把OrderDao.xml中和resultMap关联不相关的地方已经去除。
```XML
<resultMap id="wholeOrderMap" type="cn.devmgr.tutorial.model.Order">
    <id property="id" column="id" />
    <result property="orderDate" column="order_date" />
    <!-- 下面这行是实现这一切的关键 -->
    <association property="consigneeAddress" 
                 javaType="cn.devmgr.tutorial.model.ConsigneeAddress" 
                 resultMap="orderAddress" />
</resultMap>
```
下面是被关联的子resultMap的定义部分，和普通的resultMap没有任何区别：
```XML
    <resultMap id="orderAddress" type="cn.devmgr.tutorial.model.ConsigneeAddress">
        <result property="consignee" column="consignee" />
        <result property="phone" column="phone" />
        <result property="province" column="province" />
        <result property="city" column="city" />
        <result property="district" column="district" />
        <result property="address" column="address" />
    </resultMap>
```
下面是使用已经做好关联的resultMap，和使用普通resultMap也没区别：
```XML
    <select id="getOrderById" resultMap="wholeOrderMap">
        select id, consignee, phone, province, city, district, address, order_date, status
	        from order_main where id=#{id}
    </select>
```


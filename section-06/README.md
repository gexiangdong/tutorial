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

此项目中[订单类](./src/main/java/cn/devmgr/tutorial/model/Order.java)有一个属性是[收货地址](./src/main/java/cn/devmgr/tutorial/model/ConsigneeAddress.java)，收获地址是一个自定义类，两者的关系是一对一，两个类的所有属性都被存储在order_main表中，通过配置resultMap，mybatis自动把查询结果映射到了订单类，并且给订单类正确的创建了收获地址的属性，这一映射通过association实现结果集的关联。

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

<h4 id="f2">复杂类型结果嵌套</h4>

此项目中[订单类](./src/main/java/cn/devmgr/tutorial/model/Order.java)有一个属性是[订单明细](./src/main/java/cn/devmgr/tutorial/model/OrderItem.java)的列表，订单明细是一个自定义类，订单和订单明细的关系是一对多。两者分别存储在订单的主子表中。通过配置result的collection，实现了自动查询并关联子表数据到订单类。

下面是相应的resultMap示例，为了清晰便于理解，已经把OrderDao.xml中和resultMap嵌套不相关的地方已经去除。
···XML
<resultMap id="wholeOrderMap" type="cn.devmgr.tutorial.model.Order">
    <id property="id" column="id" />
    <result property="orderDate" column="order_date" />
    <!-- 下面这行是关键，包含了集合对应的属性、查询集合用的字段、集合的类型以及嵌套对应的查询等设置 -->
    <collection property="orderItems" 
		column="id" 
		ofType="cn.devmgr.tutorial.model.OrderItem" 
		select="selectOrderItem" />
</resultMap>
<!-- 嵌套子查询 -->
<select id="selectOrderItem" resultType="cn.devmgr.tutorial.model.OrderItem">
   select id, order_id, product_id, product_name, num, price
        from order_detail
        where order_id=#{id}
</select>
<!-- 使用结果集部分和使用普通结果集一致，省略 -->
```

<h4 id="f3">在一个方法中完成主子表数据插入</h4>

此项目中[订单类](./src/main/java/cn/devmgr/tutorial/model/Order.java)有一个属性是[订单明细](./src/main/java/cn/devmgr/tutorial/model/OrderItem.java)的列表，订单明细是一个自定义类，订单和订单明细的关系是一对多。两者分别存储在订单的主子表中。可以通过一个方法执行多条语句，循环子类，把订单主子表数据全部insert完成。

下面是相应的resultMap示例
```XML
<!-- 多个insert sql时，useGeneratedKeys="true" keyProperty="order.id" 不起作用，
     配置selectKey，是为了起到类似作用，返回数据库分配的主键
-->
<insert id="insertOrder" useGeneratedKeys="true" keyProperty="order.id" 
         parameterType="cn.devmgr.tutorial.model.Order">
    <selectKey keyProperty="order.id" resultType="int">
        select currval('order_main_id_seq');
    </selectKey>
    insert into order_main(consignee, phone, province, city, district, address, order_date, status)
        values(#{order.consigneeAddress.consignee}, #{order.consigneeAddress.phone},
	        #{order.consigneeAddress.province}, #{order.consigneeAddress.city}, 
		#{order.consigneeAddress.district}, #{order.consigneeAddress.address},  
	        #{order.orderDate}, 0);
    <foreach collection="order.orderItems" item="item" index="index" open="" separator=";"  close="">
        insert into order_detail(order_id, product_id, product_name, num, price)
            values(currval('order_main_id_seq'), #{item.productId}, #{item.productName}, 
	           #{item.num}, #{item.price});
    </foreach>
</insert>
```

<h4 id="f4">使用枚举类型</h4>

此项目中[订单类](./src/main/java/cn/devmgr/tutorial/model/Order.java)有一个属性是[订单类型](./src/main/java/cn/devmgr/tutorial/model/OrderType.java)是一个枚举类型。

Mybatis提供了两个枚举类型TypeHandler，分别是EnumTypeHandler和EnumOrdinaryTypeHandler：
* EnumTypeHandler存储的是对应类的名字，可以存储成一个字符串。
* EnumOrdinaryTypeHandler存储的是枚举类型的顺序，

EnumOridingaryTypeHandler会在数据内存储的是对应的枚举类型的顺序。这很不方便，以后修改程序时如果调整了顺序则容易造成数据混乱，因此不推荐大家用。

EnumTypeHandler由于使用的是类名称，一般会以字符串形式存储，因此很多人也不不愿使用，其实和数据库的enum类型结合起来就没这个问题了，但有一点需要注意：**数据库的enum类型一般是不能使用大于、小于等比较操作的**。

使用枚举类型只需要加入typeHandler=org.apache.ibatis.type.EnumTypeHandler即可

在resultMap里使用:
```XML
    <result property="orderType" column="order_type" 
	    typeHandler="org.apache.ibatis.type.EnumTypeHandler" />
```
在insert语句中使用
```XML
   insert into table1 (enum_col) 
         values (#{param.enumProp, typeHandler=org.apache.ibatis.type.EnumTypeHandler})
```

<h4 id="f5">使用数组类型</h4>

使用数组类型需要声明typeHandler="org.apache.ibatis.type.ArrayTypeHandler"，其他和枚举类型一致。

<h4 id="f6">使用JSON类型</h4>
Mybatis目前（2018/4/22）尚未提供对JSON类型的内嵌支持，需要自己写一个TypeHandler，写TypeHandler很简单，集成BaseTypeHandle并实现4个抽象方法即可。本立中有一个通用的JSON类型TypeHandler。[JsonTypeHander.java](./src/main/java/cn/devmgr/tutorial/typehandler/JsonTypeHandler.java) 通过Jackson来进行的JSON和类之间的转换。在xml里配置和使用与EnumTypeHandler、ArrayTypeHandler相同，请参考[上面的部分](#f4)。

<h4 id="f7">用include减少SQL中重复的部分</h4>
在写映射的XML文件时，经常会有SQL语句的多个部分（特别是字段列表在insert，select里重复）写多次，mybatis提供了include功能，可以只写一次。下面的例子通过sql节点定义了一段文本，然后给这段文本声明了一个id，之后用include refid来把这段文本包含进来就可以了。

```XML
<sql id="orderMainColumnsWithoutId"> 
    consignee, phone, province, city, district, address, order_date, order_type, status
</sql>
<select id="getOrderById" resultMap="wholeOrderMap">
        select id, <include refid="orderMainColumnsWithoutId" />
	        from order_main
	        where id=#{id}
</select>
```





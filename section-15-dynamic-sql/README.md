Mybatis Dynamic SQL
===========================


## 导出Excel的例子

[SampleController](./src/main/java/cn/devmgr/tutorial/controller/SampleController.java) 内有一个导出Excel格式数据的例子。


## 自定义的Mybatis Language Driver

[DynamicTableLanguageDriver](./src/main/java/cn/devmgr/tutorial/DynamicTableLanguageDriver.java) 是一个自定义的Mybatis Language Driver，它可以根据需要动态生成SQL。此例是根据传入的参数，自动生成select语句（包含表明、列名和查询条件都是通过参数动态传入的）。




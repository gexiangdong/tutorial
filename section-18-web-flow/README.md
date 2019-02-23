# Web Flow with SpringMVC

Spring Web Flow 是在 SpringMVC 基础上用于做按照一定步骤的形式的一组页面。



本例子用来





## ERROR

所有的在流程里传递的POJO都需要实现 Serializable 接口，否则运行时出现异常。
注意还要包含他们的成员变量的类、内部类等。

    Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.webflow.execution.repository.snapshot.SnapshotCreationException: Could not serialize flow execution; make sure all objects stored in flow or flash scope are serializable] with root cause
    java.io.NotSerializableException: cn.devmgr.tutorial.webflow.pojo.ShoppingCart
    
    

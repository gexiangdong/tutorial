package cn.devmgr.tutorial.tx.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionUsageException;

import cn.devmgr.tutorial.tx.AnotherService;
import cn.devmgr.tutorial.tx.PersonDao;
import cn.devmgr.tutorial.tx.PersonDto;
import cn.devmgr.tutorial.tx.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {
    private final Log log = LogFactory.getLog(AppTests.class);

    @Autowired
    PersonService serv;
    @Autowired
    AnotherService anotherService;
    @Autowired
    PersonDao dao;

    @Before
    public void setUp() {
        dao.deleteAll();
    }

    @Test
    public void testSomething() {
        //为了看的清晰，每次只执行一个测试
         testRequiredNewAndNewFailed();
//        testThrowException();
//        testInsertNoTransInsideTrans();
//        testInsertNoAnnotationInsideTrans();
//        testNested();
//        testMandatory();
    }

    /**
     * MANDATORY要求必须在一个已有的事务中执行,否则抛出异常；
     * 此测试中，调用insertMandatory方法（方法声明Propagation.MANDATORY)之前没有开启事务
     * 所以必定会抛出异常
     */
    public void testMandatory() {
        boolean b = false;
        try {
            serv.insertMandatory(new PersonDto(20002, "乙"), false);
        }catch(TransactionUsageException e) {
            log.trace(e.getClass().getName());
            b = true;
        }
        Assert.assertTrue(b);
    }
    
    /**
     * 一个事务中嵌套另外一个子事务；子事务回滚不影响父级事务
     */
    public void testNested() {
        dao.deleteAll();
        try {
            anotherService.insertNested(new PersonDto(1, "甲"), new PersonDto(2, "乙"), false);
        } catch (RuntimeException e) {

        }
        List<PersonDto> list = serv.getAll();
        if (log.isTraceEnabled()) {
            log.trace("query all list.size()=" + list.size());
        }
        //外部事务的p1应该被保存了
        Assert.assertTrue(list.size() == 1);
    }
    
    /**
     * 一个事务中执行了另外一个没有事务注解的方法，（这个和一个函数是一样的）
     */
    public void testInsertNoAnnotationInsideTrans() {
        dao.deleteAll();
        try {
            anotherService.insertNoAnnotationInsideTrans(new PersonDto(1, "甲"), new PersonDto(2, "乙"), true);
        } catch (RuntimeException e) {

        }
        List<PersonDto> list = serv.getAll();
        if (log.isTraceEnabled()) {
            log.trace("query all list.size()=" + list.size());
        }
        //不开启事务的Person 2应该被保存了
        Assert.assertTrue(list.size() == 0);
    }
    
    /**
     * 一个事务中执行了NOT_SUPPORT的方法，NOT_SUPPORT注解的方法不会加入到当前事务中。
     * 即时调用NOT_SUPPORT注解方法的方法中事务回滚了，也不会影响不开事务的。
     */
    public void testInsertNoTransInsideTrans() {
        dao.deleteAll();
        try {
            anotherService.insertNoTransInsideTrans(new PersonDto(1, "甲"), new PersonDto(2, "乙"), true);
        } catch (RuntimeException e) {

        }
        List<PersonDto> list = serv.getAll();
        if (log.isTraceEnabled()) {
            log.trace("query all list.size()=" + list.size());
        }
        //不开启事务的Person 2应该被保存了
        Assert.assertTrue(list.size() == 1);
    }

    /**
     * 抛出异常的测试 
     */
    public void testThrowException() {
        dao.deleteAll();

        // 遇到被声明throws的异常，事务是会被提交的；如果声明了throws Exception，那就几乎所有情况都被提交事务了
        PersonDto p = new PersonDto(1, "甲");
        try {
            anotherService.insertAndThrowException(p, new Exception("ERROR"));
        } catch (Exception e) {

        }
        List<PersonDto> list = serv.getAll();
        if (log.isTraceEnabled()) {
            log.trace("query all list.size()=" + list.size());
        }
        Assert.assertTrue(list.size() == 1);

        dao.deleteAll();
    }

    /**
     * 一个事务中，调用一个Propagation.REQUIRED_NEW的方法，两个不互相影响，各自负责自己的事务。
     * 内部的失败回滚了，外部一样会提交成功
     */
    public void testRequiredNewAndNewFailed() {
        dao.deleteAll();
        try {
            anotherService.insert2trans(new PersonDto(1, "甲"), new PersonDto(2, "乙"), false);
        } catch (RuntimeException e) {

        }
        List<PersonDto> list = serv.getAll();
        if (log.isTraceEnabled()) {
            log.trace("query all list.size()=" + list.size());
        }
        Assert.assertTrue(list.size() == 1);
    }

}

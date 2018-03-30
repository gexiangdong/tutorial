package cn.devmgr.tutorial.tx;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnotherService {
    private final Log log = LogFactory.getLog(AnotherService.class);
    
    @Autowired private PersonService personService;
    @Autowired private PersonDao personDao;

    /**
     * 一个REQUIRED 内嵌套一个一个 REQUIRED_NEW，还是各自管理各自的事务 
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public void insert2trans(PersonDto p1, PersonDto p2, boolean throwEception) {
        personDao.insert(p1);
        try {
            personService.insertRequireNew(p2, true);   
        }catch(Exception e) {
            //如果不写这个catch，那么异常会继续向上级抛出，就会导致这个方法也会被回滚了
            //e.printStackTrace();
        }
        if(throwEception) {
            throw new RuntimeException("ERROR");
        }
        
    }
    
    @Transactional(propagation=Propagation.REQUIRED)
    public void insertNoTransInsideTrans(PersonDto p1, PersonDto p2, boolean throwEception) {
        personDao.insert(p1);
        personService.insertNotSupported(p2, false);
        if(throwEception) {
            //这里抛出异常也不会影响p2的保存，因为p2不和这个方法在一个事务内。
            throw new RuntimeException("ERROR");
        }
        
    }
    
    @Transactional(propagation=Propagation.REQUIRED)
    public void insertNoAnnotationInsideTrans(PersonDto p1, PersonDto p2, boolean throwEception) {
        personDao.insert(p1);
        personService.insertNoAnnotation(p2, false);
        if(throwEception) {
            throw new RuntimeException("ERROR");
        }
        
    }
    
    @Transactional(propagation=Propagation.REQUIRED)
    public void insertNested(PersonDto p1, PersonDto p2, boolean throwEception) {
        personDao.insert(p1);
        try {
            personService.insertNested(p2, true);
        }catch(Exception e) {
            
        }
        if(throwEception) {
            throw new RuntimeException("ERROR");
        }
        
    }
    
    /**
     * 这个方法只要抛出的异常是Exception的子类，执行了的SQL就会被提交，不会被回滚。
     * 这不是个好例子，因为绝大部分异常都是Exception的子类，实际中要小心这种情况。
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public void insertAndThrowException(PersonDto p1, Exception e) throws Exception {
        personDao.insert(p1);
        if(e != null) {
            throw(e);
        }
    }
    
    /**
     * 方法定义抛出异常，但是只有SQLException被回滚，其他异常不回滚
     */
    @Transactional(rollbackFor=SQLException.class)
    public void insertRollbackForSQLException(PersonDto p1, Exception e) throws Exception {
        personDao.insert(p1);
        if(e != null) {
            throw(e);
        }
    }
    /**
     * 方法定义抛出异常，但是只有SQLException被回滚，其他异常不回滚
     */
    @Transactional(rollbackFor=RuntimeException.class, noRollbackFor=NullPointerException.class)
    public void insertNoRollbackForSQLException(PersonDto p1, Exception e) throws Exception {
        personDao.insert(p1);
        if(e != null) {
            throw(e);
        }
    }
    /**
     * 测试timeout参数用
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW, timeout=5)
    public void insertWithTimeout(PersonDto p1, PersonDto p2, int sleepSecond0, int sleepSecond1, int sleepSecond2) {
        try {
            Thread.sleep(sleepSecond0 * 1000);
        }catch(InterruptedException e) {
            if(log.isTraceEnabled()) {
                log.trace("sleep 被中断 InterruptedException");
            }
        }
        personDao.insert(p1);
        try {
            Thread.sleep(sleepSecond1 * 1000);
        }catch(InterruptedException e) {
            if(log.isTraceEnabled()) {
                log.trace("sleep 被中断 InterruptedException");
            }
        }
        personDao.insert(p2);
        try {
            Thread.sleep(sleepSecond2 * 1000);
        }catch(InterruptedException e) {
            if(log.isTraceEnabled()) {
                log.trace("sleep 被中断 InterruptedException");
            }
        }
    }
    
    @Transactional(propagation=Propagation.REQUIRED)
    public void lockTable(int secounds) throws InterruptedException{
        personDao.lockTablePerson();
        Thread.sleep(secounds * 1000);
    }
    
    
}

package cn.devmgr.tutorial.tx;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    private final Log log = LogFactory.getLog(PersonService.class);
    
    @Autowired private PersonDao personDao;

    @Transactional(readOnly=true)
    public List<PersonDto> getAll(){
        try {
            Thread.sleep(10);
        }catch(Exception e) {
            
        }
        if(log.isTraceEnabled()) {
            log.trace("getAllTvSeries started   ");
        }
        List<PersonDto> list = personDao.getAll();

        return list;
    }
    
    public void insertNoAnnotation(PersonDto person, boolean throwException) {
        personDao.insert(person);
        if(throwException) {
            throw new RuntimeException("ERROR");
        }
    }
    
    /**
     * Propagation.REQUIRED 果有事务, 那么加入事务, 没有的话新建一个(默认)
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public void insertRequired(PersonDto person, boolean throwException) {
        personDao.insert(person);
        if(throwException) {
            throw new RuntimeException("ERROR");
        }
    }
    
    /**
     * Propagation.NOT_SUPPORTED 容器不为这个方法开启事务
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void insertNotSupported(PersonDto person, boolean throwException) {
        personDao.insert(person);
        if(throwException) {
            throw new RuntimeException("ERROR");
        }
    }

    /**
     * Propagation.REQUIRES_NEW 不管是否存在事务,都创建一个新的事务,原来的挂起,新的执行完毕,继续执行老的事务
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void insertRequireNew(PersonDto person, boolean throwException) {
        personDao.insert(person);

        if(throwException) {
            throw new RuntimeException("ERROR");
        }
    }
    
    /**
     * Propagation.MANDATORY 必须在一个已有的事务中执行,否则抛出异常
     */
    @Transactional(propagation=Propagation.MANDATORY)
    public void insertMandatory(PersonDto person, boolean throwException) {
        personDao.insert(person);
        if(throwException) {
            throw new RuntimeException("ERROR");
        }
    }
    
    /**
     * Propagation.NEVER 必须在一个没有的事务中执行,否则抛出异常(与Propagation.MANDATORY相反)
     */
    @Transactional(propagation=Propagation.NEVER)
    public void insertNever(PersonDto person, boolean throwException) {
        personDao.insert(person);
        if(throwException) {
            throw new RuntimeException("ERROR");
        }
    }
    
    /**
     * Propagation.SUPPORTS 如果其他bean调用这个方法,在其他bean中声明事务,那就用事务.如果其他bean没有声明事务那就不用事务
     */
    @Transactional(propagation=Propagation.SUPPORTS)
    public void insertSupports(PersonDto person, boolean throwException) {
        personDao.insert(person);
        if(throwException) {
            throw new RuntimeException("ERROR");
        }
    }
    
    /**
     * PROPAGATION_NESTED 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。
     */
    @Transactional(propagation=Propagation.NESTED)
    public void insertNested(PersonDto person, boolean throwException) {
        personDao.insert(person);
        try {
            System.out.println("xxxxxxxWAITING************");
            Thread.sleep(10000);
        }catch(Exception e) {
            
        }
        if(throwException) {
            throw new RuntimeException("ERROR");
        }
    }
    
    
    public void update(PersonDto person, boolean throwException) {
        personDao.update(person);
        if(throwException) {
            throw new RuntimeException("ERROR");
        }
    }
}

package cn.devmgr.tutorial;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SampleController {
    private static final Log log = LogFactory.getLog(SampleController.class);
    
    @Autowired SampleService sampleService;
    
    // 如果把变量类型定义为RedisTemplate<?, Object> 会报错：required a bean of type 
    // 'org.springframework.data.redis.core.RedisTemplate' that could not be found.
    // 只能先用RedisTemplate<?, ?>然后再程序中再做类型转换
    // 定义成RedisTemplate<String, String>也可正常启动
    @Autowired RedisTemplate<?, ?> redisTemplate;
 
    
    @GetMapping
    public Map<String, Object> getAll() {
        //下面这个方法会异步执行，因为 doA方法上有@Async注解
        sampleService.doA();
        //下面这个方法也会异步执行，但是不会获得到返回值
        Integer r = sampleService.doD();
        if ( r != null) {
            // 不可能执行到这里
            throw new RuntimeException("doD()方法上有@Async注解，"
                    + "除Feature<>类型的返回值外，都会返回NULL，因此不会执行到这里");
        }
        
        //下面是在程序中使用redisTemplate的例子，并且设置了这个key一分钟后过期
        String key = "CACHE-DATE";
        @SuppressWarnings("unchecked")
        RedisTemplate<String, Date> rt = (RedisTemplate<String, Date>) redisTemplate;
        
        Date d = rt.opsForValue().get(key);
        if( d == null) {
            d = new Date();
            rt.opsForValue().set(key, d);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MINUTE, 1);
            rt.expireAt(key, c.getTime()); //设置1分钟后过期
            if(log.isTraceEnabled()) {
                log.trace("set date " + d + " to cache");
            }
        }else {
            if(log.isTraceEnabled()) {
                log.trace("date form cache is " + d);
            }
        }
        Map<String, Object> result = new HashMap<>();
        return result;
    }
	
    /**
     * Cacheable表示方法结果可以被缓存，第一次运行方法后，结果即被缓存；以后调用直接返回缓存的结果，不再调用方法体执行。
     * spring是通过缓存名（本例中的"counter"）和key（本例是id参数）来一起查找缓存的，不同的参数id，是不同的缓存
     */
    @Cacheable(cacheNames="counter")
    @GetMapping("/{id}")
    public Map<String, Object> getOne(@PathVariable int id){
        if(log.isTraceEnabled()) {
            log.trace("getOne " + id);
        }
        Map<String, Object> result = getData(id);
        result.put("message", "数据由getOne(" + id + ")方法在" + new Date() + "创建。");
        return result;
    }
    
   
    /**
     * CacheEvict表示收回缓存；本例是收回名字为counter，key是id参数值的缓存；此方法被调用时，一定会执行方法体，执行完毕附带收回缓存。
     * 如果需要在执行方法前收回缓存，增加参数beforeInvocation=true；（意义不大，只是时间提前一点，因为即使方法抛出异常执行完毕也回收缓存）
     */
    @CacheEvict(cacheNames="counter", key="#id")
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteOne(@PathVariable int id){
        if(log.isTraceEnabled()) {
            log.trace("deleteOne " + id);
        }
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("message", "counter was cleared");
        return map;
    }
    
    /**
     * allEntries=tru表示收回所有名字为counter的缓存，任何key的都被收回（清除）
     */
    @CacheEvict(cacheNames="counter", allEntries=true)
    @DeleteMapping()
    public Map<String, Object> deleteAll(){
        if(log.isTraceEnabled()) {
            log.trace("deleteAll ");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", "all counter was cleared");
        return map;
    }
    
    /**
     * CachePut是更新缓存，执行带有此注解的方法，会按照参数中设置的缓存名和key，并用方法的结果更新缓存；
     * CachePut注解的方法每次调用都执行，执行完毕后用方法返回值更新缓存
     */
    @CachePut(cacheNames="counter", key="#id")
    @PutMapping("/{id}")
    public Map<String, Object> putOne(@PathVariable int id){
        if(log.isTraceEnabled()) {
            log.trace("putOne " + id);
        }
        Map<String, Object> result = getData(id);
        result.put("message", "数据由putOne(" + id + ")方法在" + new Date() + "更新。");
        return result;
    }
    
    
    private Map<String, Object> getData(int id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        return map;
    }
}

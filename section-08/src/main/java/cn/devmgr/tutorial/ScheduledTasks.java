package cn.devmgr.tutorial;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private Log log = LogFactory.getLog(ScheduledTasks.class);
    private int counter;
    
    /**
     * cron参数简单的说明就是配置定时启动，几个参数依次为：
     * 秒(0-59) 分(0-59) 小时(0-23) 日(1-31) 月(1-12)  星期几(1-7) 年（可为空，空表示任意年）
     * 几个参数用空格分割，需要注意 日 和 星期几，一般其中有一个用?，？表示不用理会这里的设置了
     * 每个字段中还可以用星号*，表示任意数字都可以，也可以用/3，表示每3个时间段一次，
     * 例如：在秒处写 1/3就是从第1秒开始一次，以后没3秒一次，则4，7，10...55,58各执行一次
     * 本例中：cron = "0 0/5 * * * ?"，表示从第0分开始每5分钟在0秒时执行一次
     * 详细说明可以看： http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/crontrigger
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    @CacheEvict({"sample-data", "sample2-data-2", "sample3-data3"})
    public void clearCache() {
        if(log.isTraceEnabled()) {
            log.trace("clearCache();....clear data1-3" + counter);
        } 
    }
    
    @Scheduled(cron = "0 0/1 * * * ?")
    @CachePut("sample-data")
    public Map<String, Object> setData() {
        counter++;
        if(log.isTraceEnabled()) {
            log.trace("setData();...." + counter);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("counter", counter);
        data.put("date", new Date());
        data.put("from", "set from scheduleTask.");
        return data;
    }
    
    @Scheduled(cron = "30 0/1 * * * ?")
    public void queryData() {
        if(log.isTraceEnabled()) {
            log.trace("queryData();...." );
        }
    }

    /**
     * fixedDelay从这个方法被调用后，执行完毕返回后，开始计时；本例中因为方法执行需要5秒，从方法执行完毕后10秒再次执行，
     * 因此大约每15秒执行一次。
     * @throws InterruptedException
     */
    @Scheduled(fixedDelay = 10000)
    public void doSomthingWithFixedDelay() throws InterruptedException {
        if(log.isTraceEnabled()) {
            log.trace("doSomthingWithFixedDelay... started at " + (new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")).format(new Date()));
        }
        Thread.sleep(5000);
        if(log.isTraceEnabled()) {
            log.trace("doSomthingWithFixedDelay... stopped at " + (new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")).format(new Date()));
        }
    }

    /**
     * fixedRate是以固定频率启动的，后面跟的是毫秒数，此例是固定100秒执行一次，
     * 和这个方法本身执行多长时间是无关的（除非本身执行时长超过了这个固定频率间隔，下次会延时）
     * @throws InterruptedException
     */
    @Scheduled(fixedRate = 100000)
    public void doSomthingWithFixedRate() throws InterruptedException{
        if(log.isTraceEnabled()) {
            log.trace("doSomthingWithFixedRate... started at " + (new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")).format(new Date()));
        }
        Thread.sleep(50000);
        if(log.isTraceEnabled()) {
            log.trace("doSomthingWithFixedRate... stopped at " + (new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")).format(new Date()));
        }
    } 
 
    /**
     * initialDelay这个参数用于设置第一次执行这个方法的延迟时间，一般和fixedRate, fixedDelay一起使用用。
     * @Scheduled(fixedRate=120000)如果spring在10:00启动完毕，则10:00,10:02,10:04...会被再次执行
     * @Schedled(initialDelay=60000, fixedRate=12000)如果10:00启动，则10:01, 10:03, 10:05...会被再次执行。
     * 有几组fixedRate的程序，rate相同，可以用initialDelay调整这些程序每轮的执行顺序
     * @throws InterruptedException
     */
    @Scheduled(initialDelay = 10000, fixedRate=120000)
    public void doSomthingWithInitialDelay() throws InterruptedException {
    }

    
}

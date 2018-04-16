package cn.devmgr.tutorial;

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

}

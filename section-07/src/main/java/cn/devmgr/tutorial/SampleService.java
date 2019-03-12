package cn.devmgr.tutorial;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
    private final static Log log = LogFactory.getLog(SampleService.class);


    public Map<String, Object> getOneById(int id){
        if(log.isTraceEnabled()) {
            log.trace("SampleService.findObject...id=" + id);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        return map;
    }
   
    
}

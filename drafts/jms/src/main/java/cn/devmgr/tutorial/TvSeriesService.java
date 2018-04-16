package cn.devmgr.tutorial;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TvSeriesService {
    private Log log = LogFactory.getLog(TvSeriesService.class);

    @Autowired JmsTemplate jmsTemplate;
    
    
    /**
     * 发送消息可以是String, Map, Integer... Object(实现Serializable)需要在系统变量里声明安全
     */
    @Scheduled(fixedRate=10000)
    public void sendMessage() {
        TvSeriesDto tvSeries = new TvSeriesDto();
        tvSeries.setId(99);
        tvSeries.setName("Person of Interest");
        tvSeries.setOriginRelease(Calendar.getInstance().getTime());
        
        if(log.isTraceEnabled()) {
            log.trace("sendMessage <" + tvSeries + ">");
        }
        
        jmsTemplate.convertAndSend("addtvseriesevents", tvSeries.getName());
    }
    
    
    @JmsListener(destination="addtvseriesevents")
    public void receiveMessage(String tvSeries) {
        if(log.isTraceEnabled()) {
            log.trace("Received <" + tvSeries + ">");
        }
    }

}

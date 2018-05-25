package cn.devmgr.tutorial;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
    private Log log = LogFactory.getLog(SampleService.class);

    @Autowired JmsTemplate jmsTemplate;
    
    
    /**
     * 发送消息可以是String, Map, Integer... Object(实现Serializable)需要在系统变量里声明安全
     */
    @Scheduled(fixedRate=10000)
    public void sendMessage() {
        String message = "Hello, from sendMEssage() at " + new Date();
        if(log.isTraceEnabled()) {
            log.trace("@Scheduled sendMessage <" + message + ">");
        }
        
        jmsTemplate.convertAndSend("addtvseriesevents", message);
    }
    
    public void sendMessage(String message) {
        if(log.isTraceEnabled()) {
            log.trace("sendMessage <" + message + ">");
        }
        
        jmsTemplate.convertAndSend("addtvseriesevents", message);
    }
    
    
    @JmsListener(destination="addtvseriesevents")
    public void receiveMessage(String message) {
        if(log.isTraceEnabled()) {
            log.trace("Received <" + message + ">");
        }
    }

}

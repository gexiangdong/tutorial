package cn.devmgr.tutorial;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private Log log = LogFactory.getLog(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private SimpMessagingTemplate template;
    
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws Exception{
        if(log.isTraceEnabled()) {
            log.trace("current time is " + dateFormat.format(new Date()));
        }
        //这个消息是发送给订阅了/topic/clockmessage的所有用户
        this.template.convertAndSend("/topic/clockmessage",  new ClockMessage("Hello, it's " + dateFormat.format(new Date()) + " now!"));
    }

    
    @Scheduled(fixedRate = 10000)
    public void reportCurrentTimeTo000() throws Exception{
        if(log.isTraceEnabled()) {
            log.trace("reportCurrentTimeTo000: current time is " + dateFormat.format(new Date()));
        }
        //这个消息只有Principal.getName()为000用户才收到
        this.template.convertAndSendToUser("000", "/topic/clockmessage",  new ClockMessage("Konichiwa 000, it's " + dateFormat.format(new Date()) + " now!"));
    }
}

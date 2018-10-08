package cn.devmgr.tutorial;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SampleService {
    private static final Log log = LogFactory.getLog(SampleService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public SampleService(KafkaTemplate<?, ?> kt){
        this.kafkaTemplate = (KafkaTemplate<String, Object>) kt;
    }

    /**
     * 每10秒执行一次，往kafka服务器发送消息
     */
    @Scheduled(fixedRate = 10000)
    public void autoSendMessage(){
        if(log.isTraceEnabled()){
            log.trace("即将发送消息到datetime-topic.");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        String d = sdf.format(new Date());
        kafkaTemplate.send("datetime-topic", sdf.format(new Date()));
    }
}

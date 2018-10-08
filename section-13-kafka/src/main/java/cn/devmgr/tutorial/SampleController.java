package cn.devmgr.tutorial;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class SampleController {
    private static final Log log = LogFactory.getLog(SampleController.class);

    Map<String, Object> messages = new HashMap<>();

    @GetMapping
    public Map<String, Object> getAll() {
        return messages;
    }

    /**
     * 接收来自kafka的消息
     * @param content
     */
    @KafkaListener(topics = "datetime-topic")
    public void processMessage(String content) {
        if(log.isTraceEnabled()){
            log.trace("收到消息：" + content);
        }
        String key = "T" + System.currentTimeMillis();
        messages.put(key, content);
    }


}

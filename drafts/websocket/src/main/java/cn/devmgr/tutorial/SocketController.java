package cn.devmgr.tutorial;

import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    private final static Log log = LogFactory.getLog(SocketController.class);
    
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * SendTo SendToUser两个注解必须和 MessageMapping一起用在有作用；
     * SendToUser只是发给消息来源用户的订阅队列里； SendTo则发送到所有用户的订阅队列里
     */
    @MessageMapping("/all")
    @SendTo("/queue/clockmessage")
    public ClockMessage toAll(ClockMessage message, Principal principal) throws Exception {
        if(log.isTraceEnabled()) {
            log.trace("toAll(接受到消息)" + message);
        }
        Thread.sleep(100);
        //这个方法也能发
        this.template.convertAndSend("/queue/clockmessage",  new ClockMessage("Hello, from controller now!"));
        
        //由于使用注解@SendTo，返回结果也会被convertAndSend
        return new ClockMessage("toAll, 来自"  + principal.getName() + "的消息：" + message.getMessage() + " ");
    }
    

    @MessageMapping("/one")
    @SendToUser("/queue/clockmessage")
    public ClockMessage toOne(ClockMessage message, Principal principal) throws Exception {
        //由于增加了SendToUser注解，返回结果会被convertAndSend给特定用户，调用这个方法发消息的用户principal中的用户
        return new ClockMessage("toOne, 来自"  + principal.getName() + "的消息：" + message.getMessage() + " ");
    }
	
	
	
}

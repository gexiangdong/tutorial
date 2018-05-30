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
     * 
     * 不写SendTo/SendToUser注解，只写MessageMapping注解，也能接收客户端发送过来的消息。
     * SendTo注解的作用是给客户端发消息（发送到订阅队列里，不是回接收的客户端的消息，STOMP中无回消息的概念）
     * 
     * MessageMapping注解中配置的接收地址和WebScoketConfig中setApplicationDestinationPrefixes()设置的地址前缀
     * 一起构成了客户端向服务器端发送消息时使用地址
     */
    @MessageMapping("/all")
    @SendTo("/topic/clockmessage")
    public ClockMessage toAll(ClockMessage message, Principal principal) throws Exception {
        if(log.isTraceEnabled()) {
            log.trace("toAll(接受到消息)" + message);
        }
        Thread.sleep(100);
        //这个方法也能发
        this.template.convertAndSend("/topic/clockmessage",  new ClockMessage("Hello, from controller now!"));
        
        //由于使用注解@SendTo，返回结果也会被convertAndSend
        return new ClockMessage("toAll, 来自"  + principal.getName() + "的消息：" + message.getMessage() + " ");
    }
    

    @MessageMapping("/one")
    @SendToUser("/topic/clockmessage")
    public ClockMessage toOne(ClockMessage message, Principal principal) throws Exception {
        //由于增加了SendToUser注解，返回结果会被convertAndSend给特定用户，调用这个方法发消息的用户principal中的用户
        return new ClockMessage("toOne, 来自"  + principal.getName() + "的消息：" + message.getMessage() + " ");
    }
	
	
	
}

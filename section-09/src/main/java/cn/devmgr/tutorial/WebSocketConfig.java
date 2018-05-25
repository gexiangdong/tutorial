package cn.devmgr.tutorial;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.security.Principal;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private Log log = LogFactory.getLog(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket/sample").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new MyChannelInterceptorAdapter());
    }
    
    public class MyChannelInterceptorAdapter extends ChannelInterceptorAdapter{
        
        
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {

            StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if(log.isTraceEnabled()) {
                log.trace("拦截器截获命令:" + accessor.getCommand());
            }
            
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                // websocket连接刚刚建立起来，需要验证用户身份
                String token = accessor.getNativeHeader("access-token").get(0);
                if(log.isTraceEnabled()) {
                    log.trace("MyChannelInterceptorAdapter->preSend() ... " + token);
                }
                User user = null;
                if(token == null){
                    user = new User("GUEST");
                }else {
                    //把token转变为用户
                    user = new User(token);
                }
                accessor.setUser(user);
            }

            return message;
        }
    }
    
    
    public class User implements Principal{
        private String name;
        
        public User(String name){
            this.name = name;
        }
        @Override
        public String getName() {
            if(log.isTraceEnabled()) {
                log.trace("Principal.getName() return " + name );
            }
            return name;
        }
        
        
    } 
}

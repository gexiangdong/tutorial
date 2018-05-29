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
        // 只有用enableSimpleBroker打开的地址前缀才可以在程序中使用，使用没设置enable的前缀时不会出错，但无法传递消息
        config.enableSimpleBroker("/queue");

        // @MessageMapping注解的设置的地址的会和这个前缀一起构成客户端需要声明的地址(stompClient.send()方法的第一个参数）
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //客户端new SockJS时需要这个地址；
        registry.addEndpoint("/socket/sample").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 注册一个自定义的拦截器
        registration.interceptors(new MyChannelInterceptorAdapter());
    }

    /**
     * 自定义拦截器，实现识别用户的功能
     */
    public class MyChannelInterceptorAdapter extends ChannelInterceptorAdapter {

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if (log.isTraceEnabled()) {
                log.trace("拦截器截获命令:" + accessor.getCommand());
            }

            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                // websocket连接刚刚建立起来，需要验证用户身份；根据token识别用户
                String token = null;
                String requestHeader = accessor.getNativeHeader("Authorization").get(0);
                if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
                    token = requestHeader.substring(7);
                }
                if (log.isTraceEnabled()) {
                    log.trace("MyChannelInterceptorAdapter->preSend() ... " + requestHeader + "; token=" + token);
                }
                User user = null;
                if (token == null) {
                    user = new User("GUEST");
                } else {
                    // 把token转变为用户
                    user = new User(token);
                }
                accessor.setUser(user);
            }

            return message;
        }
    }

    public class User implements Principal {
        private String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            //往特定用户发消息时，会依赖这个名字，因此不要重名
            if (log.isTraceEnabled()) {
                log.trace("Principal.getName() return " + name);
            }
            return name;
        }

    }
}

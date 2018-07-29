package cn.devmgr.tutorial.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 用于用户身份验证的UserService
 *
 */
@Service
public class UserService implements UserDetailsService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 实际使用时，一般从数据库内查询用户信息，这里仅仅是举例，为简单方便，直接创建了user类
        // 有用户登录时，spring会调用这个方法获取用户，然后比较获取到的用户的密码和登录时提供的密码是否一致
        logger.trace("UserService.loadUserByUsername();", username);
        User u = new User();
        u.setUserName(username);
        return u;
    }

}

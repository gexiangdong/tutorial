package cn.devmgr.tutorial.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 用于用户身份验证的UserService
 *
 */
@Service
public class UserService implements UserDetailsService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 实际使用时，一般从数据库内查询用户信息，这里仅仅是举例，为简单方便，直接创建了user类
        // 有用户登录后处理流程如下：
        // 1、spring根据用户在登录页面输入的用户名，调用这个方法
        // 2、此方法根据用户名返回一个用户类（包含加密后的密码和用户权限等信息）
        // 3、Spring会去比较获取到的用户的密码和登录时提供的密码是否一致
        // 4、如果一致，则把当前用户放入session保存，登录成功
        // 5、如不一致，则在登录界面提示用户名或密码错
        logger.trace("UserService.loadUserByUsername({});", username);

        User u = new User();
        u.setUserName(username);
        u.setPassword(passwordEncoder.encode("password")); // 设置所有密码都是password

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //所有用户都具有USER ROLE
        SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority("ROLE_USER");
        authorities.add(roleUser);

        if("admin".equalsIgnoreCase(username)){
            //登录名为admin的用户具有ADMIN ROLE
            logger.trace("为当前用户{}添加ADMIN角色", username);
            SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
            authorities.add(roleAdmin);
        }

        u.setAuthorities(authorities);


        return u;
    }

}

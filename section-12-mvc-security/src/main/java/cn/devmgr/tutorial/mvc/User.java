package cn.devmgr.tutorial.mvc;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用户类
 *
 */
public class User implements UserDetails {
    private static final long serialVersionUID = -4451744501127548295L;
    private final static Logger logger = LoggerFactory.getLogger(User.class);

    private String username;
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.trace("getAuthorities()");
        return null;
    }

    @Override
    public String getPassword() {
        // 设置所有密码都是password
        return (new BCryptPasswordEncoder()).encode("password");
    }

    @Override
    public String getUsername() {
        return username;
    }
    
    public void setUserName(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

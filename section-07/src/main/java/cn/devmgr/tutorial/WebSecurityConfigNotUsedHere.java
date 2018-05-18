package cn.devmgr.tutorial;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 如果使用spring-security进行权限验证；可以通过如下方法配置一个UserDetailsService，然后剩下的都交给spring去做。
 * 这种方法是在创建了session的，写RESTful API，就不建议这种写法了。这里仅仅是写了一个最简单的例子来说明
 * spring-security的工作方法，在spring MVC里比较常用的工作方法
 * 
 * 下面Configuration, EnableWebSecurity两个注解被注释掉，是为了在这个项目中不起作用。
 */
// @Configuration
// @EnableWebSecurity
public class WebSecurityConfigNotUsedHere extends WebSecurityConfigurerAdapter {

    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService uds = new UserDetailsService() {

           @Override
           public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
               return new UserDetails() {

                   private static final long serialVersionUID = 6905138725952656074L;

                   @Override
                   public Collection<? extends GrantedAuthority> getAuthorities() {
                       return null;
                   }

                   @Override
                   public String getPassword() {
                       return passwordEncoder().encode("password");
                   }

                   @Override
                   public String getUsername() {
                       return userName;
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
                   
               };
           }
            
        };
  
        auth.userDetailsService(uds); 
   }
  
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

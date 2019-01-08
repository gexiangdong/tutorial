package cn.devmgr.tutorial.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired UserService userService;
    
    /**
     * 配置授权方式，需要包含的url等
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // 如果有一个Filter，来实现从cookie创建当前用户（或其他），需要确保这个filter在BasicAuthenticationFilter之前执行，可以用下面这句
        // 这样就不再需要在filter上增加@Component等类似注解了。如果增加了，可能会导致filter重复执行2次
        // httpSecurity.addFilterBefore( new JwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class);

        httpSecurity.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/css/*").permitAll()
                .antMatchers("/js/*").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/img/*").permitAll()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin").hasAnyRole("ADMIN").anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll();
    }
    
    
    /**
     * 设置使用自定义的UserSerive
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService); 
    }
  
    
    /**
     * 设置一个密码加密方式
     * @return
     */
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

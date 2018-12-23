package cn.devmgr.tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DbFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(DbFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String customerId = request.getHeader("customerId");
        if(customerId == null){
            logger.warn("请求没有传递customerId参数");
        }else{
            logger.trace("本次请求customerId: {}", customerId);
            ThreadLocalContext.setCustomerId(customerId);
        }

        chain.doFilter(request, response);
    }
}

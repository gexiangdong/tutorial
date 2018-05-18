package cn.devmgr.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController的一个例子，展示了各种基本操作在RestController的实现方式。
 *
 */
@RestController
@RequestMapping("/")
public class SampleController {
    private final Log log = LogFactory.getLog(SampleController.class);
    
    /**
     * 使用注解@PreAuthorize来保护方法只能被特定用户组的用户访问。
     * 测试时：
     * curl http://localhost:8080/  会返回403禁止访问
     * curl -H "Authorization: Bearer xxxx" http://localhost:8080/ 则会正常返回内容，得到一个长度为2的JSON数组（xxxx是token）
     */
    @GetMapping
    @PreAuthorize("hasRole('author')")
    public List<Map<String, Object>> getAll() {
        if(log.isTraceEnabled()) {
            log.trace("getAll() ");
        }
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> m1 = new HashMap<>();
        m1.put("id", 101);
        m1.put("name", "jack");
        list.add(m1);
        
        HashMap<String, Object> m2 = new HashMap<>();
        m2.put("id", 102);
        m2.put("name", "Dolores");
        list.add(m2);
        
        return list;
    }
    
    
    /**
     * 通过参数 Authentication auth 在程序中获取当前的用户。
     * 测试时可使用 #curl http://localhost:8080/，得到的数组中没有author字段。
     * #curl -H "Authorization: Bearer xxxx" http://localhost:8080/ 得到的数据中有authro字段，值是当前token对应的用户名
     */
    @GetMapping("/{id}")
    public Map<String, Object> getOne(@PathVariable int id, Authentication auth){
        if(log.isTraceEnabled()) {
            log.trace("getOne " + id);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", (id == 101 ? "Waytt" : (id == 102 ? "Dolores" : "unknow")));
        if(auth != null) {
            // 获取当前用户 （这里的auth即 AuthenticationTokenFilter中设置的 UsernamePasswordAuthenticationToken line: 51)
            // UserDetail是 AuthenticationTokenFilter line: 48行的UserDetails
            UserDetails ud = (UserDetails) auth.getPrincipal();
            map.put("author", ud.getUsername());
        }
        return map;
    }
    
    
}

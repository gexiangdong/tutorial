package cn.devmgr.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @Autowired SampleService sampleService;
    
    /**
     * 使用注解@PreAuthorize来保护方法只能被特定用户组的用户访问。
     * 测试时：
     * curl http://localhost:8080/  会返回403禁止访问
     * curl -H "Authorization: Bearer xxxx" http://localhost:8080/ 则会正常返回内容，得到一个长度为2的JSON数组（xxxx是token）
     */
    @GetMapping
    @PreAuthorize("hasRole('author')")
    // @PreAuthorize("hasAuthority('query')")
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
    
    @PutMapping("/{id}")
    // @PreAuthorize("map.get('id') == 10")
   //  @PostAuthorize("returnObject.get('id') == 10")
    public Map<String, Object> updateOne(@PathVariable int id, @RequestBody Map<String, Object> map){
        Map<String, Object> result = sampleService.getOneById(id);
        map.remove("id");
        result.putAll(map);
        return result;
    }
    
    
    /**
     * PreFilter/PostFilter这2个注解的作用是过滤参数/返回值的；PreFilter会按照注解参数设定，只保留符合规则的参数传给方法；
     * PostFilter则把方法返回值再次过滤，只保留符合规则的返回给客户端。
     * 例如下面的例子，PreFilter会过滤掉客户端传递过来的参数中所有不以a开头的字符串；而PostFilter则过滤掉返回数据中所有不以b结尾的字符串。
     * 执行时，客户端传递的字符串数组，只有以a开头的会被打印，并且只有以a开头并以b结尾的字符串才可以被返回给客户端；
     * PreFilter/PostFilter也和PreAuthorize/PostAuthorize一样必须用@EnableGlobalMethodSecurity(prePostEnabled = true打开才能用。
     */
    @PostMapping("/children")
    @PreFilter(filterTarget="list", value="filterObject.startsWith('a')")
    @PostFilter("filterObject.endsWith('b')")
    public List<String> echo(@RequestBody List<String> list){
        if(log.isTraceEnabled()) {
            log.trace("echo ... list.size()= " + list.size());
            for(String s : list) {
                log.trace("  " + s );
            }
        }
        return list;
    }
}

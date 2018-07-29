package cn.devmgr.tutorial.springmvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mvc")
public class MvcController {
    private final static Log log = LogFactory.getLog(MvcController.class);

   // @Autowired MvcController mvcController;
    
    /**
     * 返回的字符串以redirect:开头，表示重定向，后面是重定向到的URL
     * @return
     */
    @GetMapping("/konichiha")
    public String konichiha() {
        // redirect:表示重定向；可以用绝对地址也可用相对地址；下例用的是相对地址
        return "redirect:hi";
    }
    
    
    /**
     * 返回String时，spring会把返回的字符串当作模版的名称，去找对应的模版。
     * 可以通过在参数中增加Model变量，然后在程序中给Model增加属性来设置模版中需要的Model的值。
     * 
     * @param name
     * @param model
     * @return
     */
    @GetMapping("/hi")
    public String hi(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        log.trace("MvcController.hi();...");
        return "hi";
    }
    
    /**
     * 返回ModelAndView，可以在返回的ModelAndView里设置Model，也设置View（使用的模版）
     * @return
     */
    @GetMapping("/hello")
    public ModelAndView hello() {
        //model.addAttribute("name", name);
        log.trace("MvcController.hello();...");
        ModelAndView mv = new ModelAndView(); //"hi";
        mv.setViewName("hi");
        mv.setStatus(HttpStatus.OK);
        mv.addObject("name", "modelandview");
        return mv;
    }

    /**
     * 返回值是Map时，spring会：
     *   1、把访问的url的参数当作模版文件名（自动转换）
     *   2、把map里的key-value，放到Model里
     * @param request
     * @return
     */
    @GetMapping("/nihao")
    public Map<String, Object> nihao(HttpServletRequest request){
        log.trace("MvcController.nihao();...");
        
        Map<String, Object> result = new HashMap<>();
        result.put("name", "map");
        return result;
    }

   
}

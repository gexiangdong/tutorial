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
    
    @GetMapping("/konichiha")
    public String konichiha() {
        // redirect:表示重定向；可以用绝对地址也可用相对地址；下例用的是相对地址
        return "redirect:hi";
    }
    
    @GetMapping("/hi")
    public String hi(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        log.trace("MvcController.hi();...");
        return "hi";
    }
    
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

    @GetMapping("/nihao")
    public Map<String, Object> nihao(HttpServletRequest request){
        log.trace("MvcController.nihao();...");
        
        Map<String, Object> result = new HashMap<>();
        result.put("name", "map");
        return result;
    }

}

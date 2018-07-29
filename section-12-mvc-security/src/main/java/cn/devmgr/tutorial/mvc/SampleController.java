package cn.devmgr.tutorial.mvc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

/**
 * controller的例子
 * 
 * 这里没有用注解保护方法的例子，因为在section-07中已经有了这方面的例子，需要请看section-07中程序
 * 
 */
@Controller
public class SampleController {
    private final static Logger logger = LoggerFactory.getLogger(SampleController.class);

    @GetMapping("/all")
    public ModelAndView getAll() {
        logger.trace("getAll();");
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("all");
        
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        
        mv.addObject("list", list);
        return mv;
    }
    
    /**
     * 在WebSecurityConfig中有设置/list这个url需要ADMIN组用户才能访问
     * @return
     */
    @GetMapping("/list")
    @ModelAttribute("list")
    public List<String> listAll(){
        logger.trace("listAll();");
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        
        return list;
    }
    
    
    
}

package cn.devmgr.tutorial.mvc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;


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

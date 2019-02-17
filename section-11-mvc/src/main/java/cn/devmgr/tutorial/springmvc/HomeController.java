package cn.devmgr.tutorial.springmvc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/")
public class HomeController {
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String home(){
        logger.trace("home()");
        return "index";
    }

    @GetMapping("/time")
    public String showTime(Model model) {
        logger.trace("showTime()");
        model.addAttribute("time", new Date());
        return "index :: currentTime";
    }
}

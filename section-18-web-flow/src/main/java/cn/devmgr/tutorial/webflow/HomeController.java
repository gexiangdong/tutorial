package cn.devmgr.tutorial.webflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);


    @GetMapping("/")
    public String home(){
        logger.trace("home()");
        return "index";
    }
}

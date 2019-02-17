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

    /**
     * 返回HTML片段的例子，可和前台JS协作，实现动态更新页面部分内容。
     * 此例中 index.html中有一段JS，每隔1秒更新以下id为clockcontainer的div内容为下面这个方法返回的内容
     *
     * return 的值 :: 前面的部分是模板名称，后面是模板内的fragment名称; 也可以用返回元素的ID查找，用#ID，参见下面的方法showClock
     * @param model
     * @return
     */
    @GetMapping("/time")
    public String showTime(Model model) {
        logger.trace("showTime()");
        model.addAttribute("time", new Date());
        return "index :: currentTime";
    }

    /**
     * DOM Selectors； 和上面的showTime方法一样，只是返回值是DOM Selectors instread of fragments
     *
     *
     * @param model
     * @return
     */
    @GetMapping("/clock")
    public String showClock(Model model){
        logger.trace("showClock()");
        model.addAttribute("time", new Date());
        return "index :: #clock";
    }

}

package cn.devmgr.tutorial.springmvc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/tvseries")
@Controller
public class TvSeriesController {
    private final static Log log = LogFactory.getLog(TvSeriesController.class);

    /**
     * 模版使用url匹配；对应src/main/resources/templates/tvseries/form.html
     * 返回的tvSeries作为model，没有@ModelAttribute注解，model的attribute用类名（即tvSeries）
    */
    @GetMapping("/form")
    public TvSeries showForm(){
        TvSeries tvSeries = new TvSeries();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -5);
        tvSeries.setOriginRelease(c.getTime());
        return tvSeries;
    }
    
    @PostMapping("/create")
    public ModelAndView createTvSeries(@Valid TvSeries tvSeries, BindingResult result) {
        if(log.isTraceEnabled()) {
            log.trace("createTvSeries(_) " + tvSeries);
        }
        ModelAndView mav = new ModelAndView();
        if(result.hasErrors()) {
            // 有错误出现，设置模版文件位 对应src/main/resources/templates/tvseries/form.html，以便用户重新输入
            mav.setViewName("/tvseries/form");
            mav.addObject("ts", tvSeries);
            return mav;
        }
        // 设置模版文件为对应src/main/resources/templates/tvseries/info.html，显示刚刚新增的信息
        mav.setViewName("tvseries/info");
        mav.addObject("tvSeries", tvSeries);

        return mav;
    }
    
    /**
     * 模版使用url匹配；对应src/main/resources/templates/tvseries/info.html
     * 返回的tvSeries作为model，没有@ModelAttribute注解，model的attribute用类名（即tvSeries）
     * @param id
     * @return
     */
    @GetMapping("/info")
    public TvSeries getOne(@RequestParam("id") int id) {
        TvSeries tvSeries = new TvSeries();
        tvSeries.setId(id);
        tvSeries.setName("Person Of Interest");
        tvSeries.setOriginRelease(new Date());
        tvSeries.setSeasonCount(5);
        return tvSeries;
    }
    
    
    /**
     * 模版通过url匹配获得，使用对应src/main/resources/templates/tvseries/list.html
     * 返回值是其他Object（不是ModelAndView, Model, View, String, Map等），spring会：
     * 1、根据访问的url来自动匹配模版
     * 2、根据注解@ModelAttribute，设置返回值到model内，如果无此注解，则使用返回对象的class name作为attribute name
     * @return
     */
    @ModelAttribute("allTvSeries")
    @GetMapping("/list")
    public List<TvSeries> getAll(){
        List<TvSeries> list = new ArrayList<>();
        Random rnd = new Random();
        for(int i=0; i<20; i++) {
            TvSeries tvSeries = new TvSeries();
            tvSeries.setId(100 + i);
            tvSeries.setName("POI #" + i);
            tvSeries.setSeasonCount(rnd.nextInt(10));
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 0 - rnd.nextInt(5));
            cal.add(Calendar.MONTH, rnd.nextInt(12));
            cal.add(Calendar.DATE, rnd.nextInt(50));
            cal.add(Calendar.HOUR, rnd.nextInt(24));
            cal.add(Calendar.MINUTE, rnd.nextInt(300));
            cal.add(Calendar.SECOND, rnd.nextInt(3600));
            
            tvSeries.setOriginRelease(cal.getTime());
            list.add(tvSeries);
        }
        return list;
    }
}

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
            mav.setViewName("/tvseries/form");
            mav.addObject("ts", tvSeries);
            return mav;
        }
        
        mav.setViewName("tvseries/info");
        mav.addObject("tvSeries", tvSeries);

        return mav;
    }
    
    @GetMapping("/info")
    public TvSeries getOne(@RequestParam("id") int id) {
        TvSeries tvSeries = new TvSeries();
        tvSeries.setId(id);
        tvSeries.setName("Person Of Interest");
        tvSeries.setOriginRelease(new Date());
        tvSeries.setSeasonCount(5);
        return tvSeries;
    }
    
    
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

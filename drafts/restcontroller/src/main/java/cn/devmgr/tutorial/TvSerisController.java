package cn.devmgr.tutorial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tvseries")
public class TvSerisController {

	@GetMapping
	public List<TvSeriesDto> getAll() {
		List<TvSeriesDto> list = new ArrayList<>();
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.OCTOBER, 2, 0, 0);
		list.add(new TvSeriesDto(1, "West Wrold", c.getTime()));
		return list;
	}
	
	@PostMapping(consumes = {MediaType.TEXT_XML_VALUE},
            produces = {MediaType.TEXT_XML_VALUE})
	public TvSeriesDto createOne(@RequestBody Map<String, String> params) {
	    System.out.println("createOne: " + params);
        Calendar c = Calendar.getInstance();
        c.set(2016, Calendar.OCTOBER, 2, 0, 0);
        TvSeriesDto dto = new TvSeriesDto(1, "West Wrold", c.getTime());
        return dto;
    }
}

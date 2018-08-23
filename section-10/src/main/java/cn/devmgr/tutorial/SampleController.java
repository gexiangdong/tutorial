package cn.devmgr.tutorial;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SampleController {
    private final static Log log = LogFactory.getLog(SampleController.class);
    
    @Autowired SampleService tvSeriesService;
    
	@GetMapping
	public Map<String, Object> getAll() {
		
		if(log.isTraceEnabled()) {
		    log.trace("getAll() was called;");
		}
		tvSeriesService.sendMessage("message from sampleController ");
		
		Map<String, Object> result = new HashMap<>();
		return result;
	}
	
	
	
}

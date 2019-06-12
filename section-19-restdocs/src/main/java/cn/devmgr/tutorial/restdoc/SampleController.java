package cn.devmgr.tutorial.restdoc;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class SampleController {


    @GetMapping
    public Map<String, Object> getAll(){
        Map<String, Object> map = new HashMap<>();
        map.put("message", "OK");
        return map;
    }
}

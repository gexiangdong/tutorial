package cn.devmgr.tutorial;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
    
    @Async
    public void doA() {
        // 方法
    }

    @Async
    private void doB() {
    }
    
    public void doC() {
        doA();
    }
    
    @Async
    public int doD() {
        return 5;
    }
}

package cn.devmgr.tutorial;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
    
    @Async
    public void doA() {
        // 方法
    }

    /**
     * 写在私有方法上的@Async注解是不起作用的，而且不会收到任何错误信息
     */
    @Async
    private void doB() {
    }
    
    public void doC() {
        // 在这个方法内部调用写了@Async注解的方法doA，是不会异步执行的。
        // 因为spring使用的是proxy机制，异步注解在内部调用时不起作用，只能在外部调用时异步执行
        doA();
    }
    
    /**
     * 外部调用这个方法时，不会得到返回值，@Async注解的方法异步执行的，
     * 如果需要返回值，要用Feature<Integer>这种类型来代替
     */
    @Async
    public Integer doD() {
        return 5;
    }
}

package cn.devmgr.tutorial.bpm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ResumeService {
    private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);


    public void storeResume(){
        logger.trace("storeResume()................");
    }
}

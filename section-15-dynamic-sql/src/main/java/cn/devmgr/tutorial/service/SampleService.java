package cn.devmgr.tutorial.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.devmgr.tutorial.dao.DynamicSqlDao;

@Service
public class SampleService {
    private final Log log = LogFactory.getLog(SampleService.class);
    
    @Autowired private DynamicSqlDao dynamicSqlDao;

    @Transactional(readOnly=true)
    public List<Map<String, Object>> getAllTvSeries(){
        if(log.isTraceEnabled()) {
            log.trace("getAllTvSeries started   ");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("id",  1);
        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("name");
        String tableName = "tv_series";
        List<Map<String, Object>> list = dynamicSqlDao.getByConditions(tableName, columns, params);

        return list;
    }


}

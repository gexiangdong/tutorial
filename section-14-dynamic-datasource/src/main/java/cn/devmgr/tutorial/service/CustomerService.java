package cn.devmgr.tutorial.service;

import cn.devmgr.tutorial.ThreadLocalContext;
import cn.devmgr.tutorial.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    /**
     * 使用主数据库查询的例子；
     * @param id
     * @return
     */
    public Map<String, Object> getCustomerById(int id){
        ThreadLocalContext.setDbKey("main");  //切换到主数据库
        Map<String, Object> map = customerDao.getCustomerById(id);
        ThreadLocalContext.setDbKey(null);    //切换回原有数据库
        return map;
    }
}

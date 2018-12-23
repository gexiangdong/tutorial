package cn.devmgr.tutorial.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CustomerDao {

    @Select("select * from customer where id=#{id}")
    public Map<String, Object> getCustomerById(int id);
}

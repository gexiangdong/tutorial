package cn.devmgr.tutorial.dao;

import cn.devmgr.tutorial.DynamicTableLanguageDriver;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface DynamicSqlDao {

    /**
     * 使用自定义LanguageDriver的例子，@Lang注解声明使用自定义的LanaguageDriver; 三个参数在自定义LanguageDriver中可以读取到，然后动态生成SQL
     * @param tableName
     * @param columns
     * @param param
     * @return
     */
    @Lang(DynamicTableLanguageDriver.class)
    @Select("")
    public List<Map<String, Object>> getByConditions(@Param("tableName") String tableName,
                                          @Param("columns") List<String> columns,
                                          @Param("conditions") Map<String, Object> param);


}

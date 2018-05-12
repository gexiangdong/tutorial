package cn.devmgr.tutorial.generality;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;
 
/**
 * 一个通用的DAO，用于处理一些简单的表的insert/update等；
 *
 */
@Repository
public interface GenericDao {
 
    /**
     * 依据主键id来update一个表；如果bean的某个属性是null，就把null值update到表内
     * @param bean
     * @return
     */
    @UpdateProvider(type=InsertUpdateSqlProvider.class, method="updateById")
    public int updateById(Object bean);
     
    /**
     * 依据主键id来update一个表；如果bean的某个属性是null，则不update该字段
     * @param bean
     * @return
     */
    @UpdateProvider(type=InsertUpdateSqlProvider.class, method="updateNonNullById")
    public int updateNonNullById(Object bean);
     
    /**
     * insert某个表，主键是ID，而且是数据库内自动生产的（例如自动增长）
     * @param bean
     * @return
     */
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    @InsertProvider(type=InsertUpdateSqlProvider.class, method="insert")
    public int insert(Object bean);
     
    /**
     * insert某个表，主键由bean内属性提供，不使用数据库的自动生成功能
     * @param bean
     * @return
     */
    @InsertProvider(type=InsertUpdateSqlProvider.class, method="insert")
    public int insertWithoutGeneratedKey(Object bean);
}
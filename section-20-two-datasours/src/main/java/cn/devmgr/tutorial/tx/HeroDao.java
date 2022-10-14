package cn.devmgr.tutorial.tx;

import cn.devmgr.tutorial.SecondaryMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@SecondaryMapper
public interface HeroDao {

  @Select("select * from hero")
  public List<HeroDto> getAll();

  @Update("update hero set name=#{name}, birthday=#{birthday} where id=#{id}")
  public int update(HeroDto person);
}

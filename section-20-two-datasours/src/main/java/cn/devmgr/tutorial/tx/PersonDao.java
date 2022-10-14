package cn.devmgr.tutorial.tx;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PersonDao {

  public List<PersonDto> getAll();

  @Update("update person set name=#{name}, birthday=#{birthday} where id=#{id}")
  public int update(PersonDto person);
}

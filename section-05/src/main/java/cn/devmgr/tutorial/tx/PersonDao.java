package cn.devmgr.tutorial.tx;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonDao {
    
    @Select("select * from person where id=#{id}")
    public PersonDto getOneById(int id);
    
    @Select("select * from person")
    public List<PersonDto> getAll();
    
    @Update("update person set name=#{name}, birthday=#{birthday} where id=#{id}")
    public int update(PersonDto person);
    
    @Insert("insert into person (id, name, birthday) values(#{id}, #{name}, #{birthday})")
    public int insert(PersonDto person);
    
    @Delete("delete from person where id=#{id}")
    public int delete(int id);
    
    @Delete("delete from person")
    public int deleteAll();
    
    @Select("lock table person in  ACCESS EXCLUSIVE mode")
    public void lockTablePerson();
}
